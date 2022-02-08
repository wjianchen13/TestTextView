# SpannableStringBuilder 解析

SpannableStringBuilder 的使用代码：
```Java
SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
SpannableString spanStr2 = new SpannableString(tip2);
spanStr2.setSpan(createDinoNolineClickableSpan(true, Color.parseColor("#FF2A7A"), this)
        , 0, spanStr2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
spannableStringBuilder.append(spanStr2);
ImageSpan imageSpan = new MyIm(this, R.drawable.ic_launcher);
spannableStringBuilder.setSpan(imageSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
```

SpannableStringBuilder构造方法
1.获取字符串实际的长度
2.数据不对抛出异常
3.创建mText数组
4.把text的内容存入到mText数组
5.初始化一系列的数据，刚开始默认都是长度为0的数组

append()方法，该方法会获取已经储存的字符长度，最终会调用replace()方法
这个方法传入的参数分别为：
start 替换的开始位置 
end 替换结束位置
tb 替换的字符串资源
tbstart 替换字符资源的开始位置
tbend 替换字符资源的结束位置
1.数据不对抛出异常
2.计算原始替换长度和目标替换长度，如果这2个都等于0并且没有SPAN_EXCLUSIVE_EXCLUSIVE标志，直接返回本对象
3.获取了TextWatcher的对象数组，通知beforeTextChanged
4.接着会调用change()方法
change(start, end, tb, tbstart, tbend);
最终会调用SpannableStringBuilder的setSpan()方法
5.在setSpan()方法中：
1.检查越界条件
2.检查设置的对象之前是否 已经设置过，在这里查找mIndexOfSpan 是否存在之前已经设置过的对象
3.接着设置一下变量
mSpanCount span数量
mSpanInsertCount 
mSpans span对象
mSpanStarts span开始位置
mSpanEnds span结束位置
mSpanMax = EmptyArray.INT;
mSpanFlags span的标志
mSpanOrder span插入顺序
4.最后会返回到change()方法，调用restoreInvariants()，实例化mIndexOfSpan，并把没有加入的span加入到mIndexOfSpan




















































