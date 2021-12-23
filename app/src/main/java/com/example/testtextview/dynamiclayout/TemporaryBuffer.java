package com.example.testtextview.dynamiclayout;

import com.example.testtextview.textview.ArrayUtils;

public class TemporaryBuffer {
    public static char[] obtain(int len) {
        char[] buf;

        synchronized (TemporaryBuffer.class) {
            buf = sTemp;
            sTemp = null;
        }

        if (buf == null || buf.length < len) {
            buf = ArrayUtils.newUnpaddedCharArray(len);
        }

        return buf;
    }

    public static void recycle(char[] temp) {
        if (temp.length > 1000) return;

        synchronized (TemporaryBuffer.class) {
            sTemp = temp;
        }
    }

    private static char[] sTemp = null;
}