# SpannableStringBuilder 解析
1.当调用下面方法创建SpannableString对象时
```Java
SpannableString spanStr1 = new SpannableString(tip1);
```
SpannableString继承SpannableStringInternal，最终会调用SpannableStringInternal
的构造方法
```Java
SpannableStringInternal(CharSequence source, int start, int end) {
```
在这里会对一系列的成员变量进行赋值，包括mText，mSpans，mSpanData
另外还会判断传入的source是不是Spanned，进行系列转换，Spanned和Spannable的区别如下：
Spanned：表示可标记文本对象，主要定义getSpan*相关方法，主要实现类是SpannedString。
Spannable：扩展了Spanned接口，增加了修改Span对象的方法，所以Spannable对象是可以修改内部文本的标记对象的，比如SpannableString类。
mSpans是一个Object数组，用来存储Span对象
mSpanData是一个Int数组，用来存储start，end和flag这3个数据，下一个对象的存储位置将从3的倍数的index开始。

setSpan()方法
```Java
SpannableString spanStr2 = new SpannableString(tip2);
spanStr2.setSpan(createDinoNolineClickableSpan(true, Color.parseColor("#FF2A7A"), this)
        , 0, spanStr2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
```
SpannableString继承SpannableStringInternal，最终会调用SpannableStringInternal的setSpan()方法
在setSpan方法里做了下面的事情：
1.判断传入的start和end是否合法
2.检测传入的flag
3.获取已经存在的span信息，如果是之前已经存入的Span，则更新Span的信息，start，end和flag,并通知span改变
4.对mSpans和mSpanData数组扩容，当mSpanCount + 1 >= mSpans.length时会对数组进行扩容
5.如果是之前没有存入的span，则存储span，并通知span改变，代码如下：
```Java
mSpans[mSpanCount] = what;
mSpanData[mSpanCount * COLUMNS + START] = start;
mSpanData[mSpanCount * COLUMNS + END] = end;
mSpanData[mSpanCount * COLUMNS + FLAGS] = flags;
mSpanCount++;
```
span对象直接存在mSpan中，start，end，flag存在以mSpanCount*3的开始位置中。


































