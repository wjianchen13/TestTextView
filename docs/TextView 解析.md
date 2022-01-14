# TextView 解析
1.onMeasure()

2.onLayout()

3.onDraw()
1.第一句restartMarqueeIfNeeded()绘制字幕滚动。
2.compoundDrawable的绘制，也就是drawableTop/Bottom/Left/Right属性。
3.初始化mTextPaint
4.使用Layout绘制文字
```Java
layout.draw(canvas, highlight, mHighlightPaint, cursorOffsetVertical);
```
5.最终会调到Layout类的draw()方法
```Java
    public void draw(Canvas canvas, Path highlight, Paint highlightPaint,
            int cursorOffsetVertical) {
        final long lineRange = getLineRangeForDraw(canvas);
        int firstLine = TextUtils.unpackRangeStartFromLong(lineRange);
        int lastLine = TextUtils.unpackRangeEndFromLong(lineRange);
        if (lastLine < 0) return;

        drawBackground(canvas, highlight, highlightPaint, cursorOffsetVertical,
                firstLine, lastLine);
        drawText(canvas, firstLine, lastLine);
    }
```
先调用Layout的drawBackground()方法绘制背景，然后再调用Layout的drawText()方法绘制文字






































