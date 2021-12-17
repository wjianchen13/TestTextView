package com.example.testtextview.textview;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface GraphicsOperations extends CharSequence {
    /**
     * Just like {@link Canvas#drawText}.
     */
    void drawText(Canvas c, int start, int end,
                  float x, float y, Paint p);

    /**
     * Just like {@link Canvas#drawTextRun}.
     */
    void drawTextRun(Canvas c, int start, int end, int contextStart, int contextEnd,
                     float x, float y, boolean isRtl, Paint p);

    /**
     * Just like {@link Paint#measureText}.
     */
    float measureText(int start, int end, Paint p);

    /**
     * Just like {@link Paint#getTextWidths}.
     */
    public int getTextWidths(int start, int end, float[] widths, Paint p);

    /**
     * Just like { Paint#getTextRunAdvances}.
     */
    float getTextRunAdvances(int start, int end, int contextStart, int contextEnd,
                             boolean isRtl, float[] advances, int advancesIndex, Paint paint);

    /**
     * Just like { Paint#getTextRunCursor}.
     */
    int getTextRunCursor(int contextStart, int contextEnd, int dir, int offset,
                         int cursorOpt, Paint p);
}