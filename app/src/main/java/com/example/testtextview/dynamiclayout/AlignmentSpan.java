package com.example.testtextview.dynamiclayout;

import android.os.Parcel;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.TextUtils;
import android.text.style.ParagraphStyle;

public interface AlignmentSpan extends ParagraphStyle {
    
    com.example.testtextview.dynamiclayout.Layout.Alignment getAlignment();

    class Standard implements android.text.style.AlignmentSpan, ParcelableSpan {
        public Standard(android.text.Layout.Alignment align) {
            mAlignment = align;
        }

        public Standard(Parcel src) {
            mAlignment = android.text.Layout.Alignment.valueOf(src.readString());
        }

        public int getSpanTypeId() {
            return getSpanTypeIdInternal();
        }

        /** @hide */
        public int getSpanTypeIdInternal() {
//            return TextUtils.ALIGNMENT_SPAN;
            return 1;
        }
        
        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            writeToParcelInternal(dest, flags);
        }

        /** @hide */
        public void writeToParcelInternal(Parcel dest, int flags) {
            dest.writeString(mAlignment.name());
        }

        public android.text.Layout.Alignment getAlignment() {
            return mAlignment;
        }

        private final Layout.Alignment mAlignment;
    }
}
