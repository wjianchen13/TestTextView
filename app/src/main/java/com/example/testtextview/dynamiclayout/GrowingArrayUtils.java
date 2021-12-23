package com.example.testtextview.dynamiclayout;

import android.text.style.ParagraphStyle;

import com.example.testtextview.textview.ArrayUtils;

public final class GrowingArrayUtils {

    /**
     * Appends an element to the end of the array, growing the array if there is no more room.
     * @param array The array to which to append the element. This must NOT be null.
     * @param currentSize The number of elements in the array. Must be less than or equal to
     *                    array.length.
     * @param element The element to append.
     * @return the array to which the element was appended. This may be different than the given
     *         array.
     */
    public static ParagraphStyle[] append(ParagraphStyle[] array, int currentSize, ParagraphStyle element) {
        assert currentSize <= array.length;

        if (currentSize + 1 > array.length) {
            @SuppressWarnings("unchecked")
            ParagraphStyle[] newArray = ArrayUtils.newUnpaddedArray(
                    (Class<ParagraphStyle>) array.getClass().getComponentType(), growSize(currentSize));
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array = newArray;
        }
        array[currentSize] = element;
        return array;
    }


    public static int[] append1(int[] array, int currentSize, int element) {
        assert currentSize <= array.length;

        if (currentSize + 1 > array.length) {
            @SuppressWarnings("unchecked")
            int[] newArray = ArrayUtils.newUnpaddedArray1(
                    /*array.getClass().getComponentType(),*/ growSize(currentSize));
            System.arraycopy(array, 0, newArray, 0, currentSize);
            array = newArray;
        }
        array[currentSize] = element;
        return array;
    }

    /**
     * Given the current size of an array, returns an ideal size to which the array should grow.
     * This is typically double the given size, but should not be relied upon to do so in the
     * future.
     */
    public static int growSize(int currentSize) {
        return currentSize <= 4 ? 8 : currentSize * 2;
    }

    // Uninstantiable
    private GrowingArrayUtils() {}
}