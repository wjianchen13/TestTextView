/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.testtextview.dynamiclayout;

import android.graphics.Paint;
import com.example.testtextview.dynamiclayout.Layout;
import android.text.Spanned;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.MetricAffectingSpan;
import android.text.style.ReplacementSpan;
import android.util.Log;

import com.example.testtextview.textview.ArrayUtils;

/**
 * @hide
 */
class MeasuredText {
    private static final boolean localLOGV = false;
    CharSequence mText;
    int mTextStart;
    float[] mWidths;
    char[] mChars;
    byte[] mLevels;
    int mDir;
    boolean mEasy;
    int mLen;

    private int mPos;
    private TextPaint mWorkPaint;
    private StaticLayout.Builder mBuilder;

    private MeasuredText() {
        mWorkPaint = new TextPaint();
    }

    private static final Object[] sLock = new Object[0];
    private static final MeasuredText[] sCached = new MeasuredText[3];

    static MeasuredText obtain() {
        MeasuredText mt;
        synchronized (sLock) {
            for (int i = sCached.length; --i >= 0;) {
                if (sCached[i] != null) {
                    mt = sCached[i];
                    sCached[i] = null;
                    return mt;
                }
            }
        }
        mt = new MeasuredText();
        if (localLOGV) {
            Log.v("MEAS", "new: " + mt);
        }
        return mt;
    }

    static MeasuredText recycle(MeasuredText mt) {
        mt.finish();
        synchronized(sLock) {
            for (int i = 0; i < sCached.length; ++i) {
                if (sCached[i] == null) {
                    sCached[i] = mt;
                    mt.mText = null;
                    break;
                }
            }
        }
        return null;
    }

    void finish() {
        mText = null;
        mBuilder = null;
        if (mLen > 1000) {
            mWidths = null;
            mChars = null;
            mLevels = null;
        }
    }

    void setPos(int pos) {
        mPos = pos - mTextStart;
    }

    static boolean doesNotNeedBidi(char[] text, int start, int len) {
        final int end = start + len;
        for (int i = start; i < end; i++) {
            if (couldAffectRtl(text[i])) {
                return false;
            }
        }
        return true;
    }

    static boolean couldAffectRtl(char c) {
        return (0x0590 <= c && c <= 0x08FF) ||  // RTL scripts
                c == 0x200E ||  // Bidi format character
                c == 0x200F ||  // Bidi format character
                (0x202A <= c && c <= 0x202E) ||  // Bidi format characters
                (0x2066 <= c && c <= 0x2069) ||  // Bidi format characters
                (0xD800 <= c && c <= 0xDFFF) ||  // Surrogate pairs
                (0xFB1D <= c && c <= 0xFDFF) ||  // Hebrew and Arabic presentation forms
                (0xFE70 <= c && c <= 0xFEFE);  // Arabic presentation forms
    }
    
    /**
     * Analyzes text for bidirectional runs.  Allocates working buffers.
     */
    void setPara(CharSequence text, int start, int end, TextDirectionHeuristic textDir,
            StaticLayout.Builder builder) {
        mBuilder = builder;
        mText = text;
        mTextStart = start;

        int len = end - start;
        mLen = len;
        mPos = 0;

        if (mWidths == null || mWidths.length < len) {
            mWidths = ArrayUtils.newUnpaddedFloatArray(len);
        }
        if (mChars == null || mChars.length < len) {
            mChars = ArrayUtils.newUnpaddedCharArray(len);
        }
        TextUtils.getChars(text, start, end, mChars, 0);

        if (text instanceof Spanned) {
            Spanned spanned = (Spanned) text;
            ReplacementSpan[] spans = spanned.getSpans(start, end,
                    ReplacementSpan.class);

            for (int i = 0; i < spans.length; i++) {
                int startInPara = spanned.getSpanStart(spans[i]) - start;
                int endInPara = spanned.getSpanEnd(spans[i]) - start;
                // The span interval may be larger and must be restricted to [start, end[
                if (startInPara < 0) startInPara = 0;
                if (endInPara > len) endInPara = len;
                for (int j = startInPara; j < endInPara; j++) {
                    mChars[j] = '\uFFFC'; // object replacement character
                }
            }
        }

        if ((textDir == TextDirectionHeuristics.LTR ||
                textDir == TextDirectionHeuristics.FIRSTSTRONG_LTR ||
                textDir == TextDirectionHeuristics.ANYRTL_LTR) &&
                doesNotNeedBidi(mChars, 0, len)) {
            mDir = Layout.DIR_LEFT_TO_RIGHT;
            mEasy = true;
        } else {
            if (mLevels == null || mLevels.length < len) {
                mLevels = ArrayUtils.newUnpaddedByteArray(len);
            }
            int bidiRequest;
            if (textDir == TextDirectionHeuristics.LTR) {
                bidiRequest = Layout.DIR_REQUEST_LTR;
            } else if (textDir == TextDirectionHeuristics.RTL) {
                bidiRequest = Layout.DIR_REQUEST_RTL;
            } else if (textDir == TextDirectionHeuristics.FIRSTSTRONG_LTR) {
                bidiRequest = Layout.DIR_REQUEST_DEFAULT_LTR;
            } else if (textDir == TextDirectionHeuristics.FIRSTSTRONG_RTL) {
                bidiRequest = Layout.DIR_REQUEST_DEFAULT_RTL;
            } else {
                boolean isRtl = textDir.isRtl(mChars, 0, len);
                bidiRequest = isRtl ? Layout.DIR_REQUEST_RTL : Layout.DIR_REQUEST_LTR;
            }
//            mDir = AndroidBidi.bidi(bidiRequest, mChars, mLevels, len, false);
            mDir = Layout.DIR_LEFT_TO_RIGHT;
            mEasy = false;
        }
    }
    

//    float addStyleRun(TextPaint paint, int len, Paint.FontMetricsInt fm) {
//        if (fm != null) {
//            paint.getFontMetricsInt(fm);
//        }
//
//        int p = mPos;
//        mPos = p + len;
//
//        // try to do widths measurement in native code, but use Java if paint has been subclassed
//        // FIXME: may want to eliminate special case for subclass
//        float[] widths = null;
//        if (mBuilder == null || paint.getClass() != TextPaint.class) {
//            widths = mWidths;
//        }
//        if (mEasy) {
//            boolean isRtl = mDir != Layout.DIR_LEFT_TO_RIGHT;
//            float width = 0;
//            if (widths != null) {
//                width = paint.getTextRunAdvances(mChars, p, len, p, len, isRtl, widths, p);
//                if (mBuilder != null) {
//                    mBuilder.addMeasuredRun(p, p + len, widths);
//                }
//            } else {
//                width = mBuilder.addStyleRun(paint, p, p + len, isRtl);
//            }
//            return width;
//        }
//
//        float totalAdvance = 0;
//        int level = mLevels[p];
//        for (int q = p, i = p + 1, e = p + len;; ++i) {
//            if (i == e || mLevels[i] != level) {
//                boolean isRtl = (level & 0x1) != 0;
//                if (widths != null) {
//                    totalAdvance +=
//                            paint.getTextRunAdvances(mChars, q, i - q, q, i - q, isRtl, widths, q);
//                    if (mBuilder != null) {
//                        mBuilder.addMeasuredRun(q, i, widths);
//                    }
//                } else {
//                    totalAdvance += mBuilder.addStyleRun(paint, q, i, isRtl);
//                }
//                if (i == e) {
//                    break;
//                }
//                q = i;
//                level = mLevels[i];
//            }
//        }
//        return totalAdvance;
//    }

//    float addStyleRun(TextPaint paint, MetricAffectingSpan[] spans, int len,
//            Paint.FontMetricsInt fm) {
//
//        TextPaint workPaint = mWorkPaint;
//        workPaint.set(paint);
//        // XXX paint should not have a baseline shift, but...
//        workPaint.baselineShift = 0;
//
//        ReplacementSpan replacement = null;
//        for (int i = 0; i < spans.length; i++) {
//            MetricAffectingSpan span = spans[i];
//            if (span instanceof ReplacementSpan) {
//                replacement = (ReplacementSpan)span;
//            } else {
//                span.updateMeasureState(workPaint);
//            }
//        }
//
//        float wid;
//        if (replacement == null) {
//            wid = addStyleRun(workPaint, len, fm);
//        } else {
//            // Use original text.  Shouldn't matter.
//            wid = replacement.getSize(workPaint, mText, mTextStart + mPos,
//                    mTextStart + mPos + len, fm);
//            if (mBuilder == null) {
//                float[] w = mWidths;
//                w[mPos] = wid;
//                for (int i = mPos + 1, e = mPos + len; i < e; i++)
//                    w[i] = 0;
//            } else {
//                mBuilder.addReplacementRun(mPos, mPos + len, wid);
//            }
//            mPos += len;
//        }
//
//        if (fm != null) {
//            if (workPaint.baselineShift < 0) {
//                fm.ascent += workPaint.baselineShift;
//                fm.top += workPaint.baselineShift;
//            } else {
//                fm.descent += workPaint.baselineShift;
//                fm.bottom += workPaint.baselineShift;
//            }
//        }
//
//        return wid;
//    }

    int breakText(int limit, boolean forwards, float width) {
        float[] w = mWidths;
        if (forwards) {
            int i = 0;
            while (i < limit) {
                width -= w[i];
                if (width < 0.0f) break;
                i++;
            }
            while (i > 0 && mChars[i - 1] == ' ') i--;
            return i;
        } else {
            int i = limit - 1;
            while (i >= 0) {
                width -= w[i];
                if (width < 0.0f) break;
                i--;
            }
            while (i < limit - 1 && (mChars[i + 1] == ' ' || w[i + 1] == 0.0f)) {
                i++;
            }
            return limit - i - 1;
        }
    }

    float measure(int start, int limit) {
        float width = 0;
        float[] w = mWidths;
        for (int i = start; i < limit; ++i) {
            width += w[i];
        }
        return width;
    }
}
