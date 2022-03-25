# TextView 解析
1.onMeasure()
解释下什么叫作boring:
boring就是指布局所用的文本里面不包含任何Span，所有的文本方向都是从左到右的布局，并且仅需一行就能显示完全的布局

在onMeasure()中，如果包含了Span，最终会调用到makeNewLayout()方法进行Layout的初始化
makeNewLayout()方法会调用makeSingleLayout()方法，在makeSingleLayout()方法中，会判断mText是否Spannable类型，如果
是，直接初始化DynamicLayout。初始化完成之后，就可以调用DynamicLayout的方法，获取mText对应的一些属性，比如：
result.getWidth(), result.getLineBottom(),result.getLineCount()等等







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
ImageSpan替换的完整调用链
TextView:onDraw()->Layout:draw()->Layout:drawText()->TextLine:draw()->TextLine:drawRun()-》TextLine:handleRun()
->TextLine:handleReplacement()->ReplacementSpan:draw()


4.onTouchEvent()
TextView onTouchEvent()方法最终调用LinkMovementMethod onTouchEvent()方法。
在该方法中，首先获取对应的DynamicLayout，然后获取行数和水平偏移，获取对应的ClickableSpan，调用ClickableSpan的onClick()方法
```Java
Layout layout = widget.getLayout();
int line = layout.getLineForVertical(y);
int off = layout.getOffsetForHorizontal(line, x);

ClickableSpan[] links = buffer.getSpans(off, off, ClickableSpan.class);
if (links.length != 0) {
    if (action == MotionEvent.ACTION_UP) {
        links[0].onClick(widget);
    } else if (action == MotionEvent.ACTION_DOWN) {
        Selection.setSelection(buffer,
                buffer.getSpanStart(links[0]),
                buffer.getSpanEnd(links[0]));
    }
    return true;
} else {
    Selection.removeSelection(buffer);
}
```






# 例子
使用SpannableStringBuilder
```Java
String tip2 = "test2";
SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
SpannableString spanStr2 = new SpannableString(tip2);
spanStr2.setSpan(createDinoNolineClickableSpan(true, Color.parseColor("#FF2A7A"), this)
        , 0, spanStr2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
spannableStringBuilder.append(spanStr2);
ImageSpan imageSpan = new MyIm(this, R.drawable.ic_launcher);
spannableStringBuilder.setSpan(imageSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
tv.setText(spannableStringBuilder);
```
这样设置之后
spanStr2内存模型如下：
mText="test2"
mSpans[0]=匿名内部类的实例
mSpanData[0]=0
mSpanData[1]=5
mSpanData[2]=33 // 这个就是传入的标志flag:SPAN_EXCLUSIVE_EXCLUSIVE
mSpanCount=1

spannableStringBuilder添加之后，内存如下
mSpanCount 1
mSpanInsertCount 1
mSpans[0] 匿名内部类的实例
mSpanStarts[0] 0
mSpanEnds[0] 5
mSpanMax 树数据结构相关的变量，暂不清楚什么意思
mSpanFlags 33 这个参数传入的时候是由原来的flags与0x800或运算得到2081，这个标志会在sendToSpanWatchers把它去掉，所以最后还是33
mSpanOrder[0] 0

spannableStringBuilder添加MyIm之后，内存如下
mSpanCount 2
mSpanInsertCount 2
mSpans[1] MyIm的实例
mSpanStarts[1] 0
mSpanEnds[1] 2
mSpanMax 树数据结构相关的变量，暂不清楚什么意思
mSpanFlags 33 这个参数传入的时候是由原来的flags与0x800或运算得到的
mSpanOrder[0] 1




















































