#从源码复制出来的TextView源码版本是26 android 8.0

# 源码查看地址
http://androidxref.com/

# TextView 文字绘制流程
https://www.cnblogs.com/bvin/p/5370490.html

# TextView的绘制流程
1.TextView 的onDraw会调用DynamicLayout的onDraw()方法，实际上调用的DynamicLayout的父类Layout的onDraw()方法
在这里首先会调用drawBackground()绘制背景,然后再调用Layout的drawText()绘制文字。
2.Layout的drawText()最终调用TextLine的draw()方法，在该方法内部最终会调用drawRun()方法，drawRun()方法会调用
handleRun()方法
drawText()方法中：会调用getLineExtent()获取某一行的范围，最终会调用handleRun()方法，这方法会返回float类型的值。

3.在handleRun()方法里面
调用handleReplacement()方法处理了图片替换
调用handleText()绘制文字

需要这些信息，整个TextView的宽高，包括图片在内
起始位置
点击Span的区域




*遇到问题
1.
编译报错
AAPT: E:\github\TestTextView\app\build\intermediates\incremental\mergeDebugResources\merged.dir\values-v28\values-v28.xml:7: error: resource android:attr/dialogCornerRadius not found.
E:\github\TestTextView\app\build\intermediates\incremental\mergeDebugResources\merged.dir\values-v28\values-v28.xml:11: error: resource android:attr/dialogCornerRadius not found.
E:\github\TestTextView\app\build\intermediates\incremental\mergeDebugResources\merged.dir\values\values.xml:5363: error: resource android:attr/fontVariationSettings not found.
E:\github\TestTextView\app\build\intermediates\incremental\mergeDebugResources\merged.dir\values\values.xml:5364: error: resource android:attr/ttcIndex not found.
E:\github\TestTextView\app\build\intermediates\incremental\mergeDebugResources\merged.dir\values\values.xml:7382: error: resource android:attr/textFontWeight not found.

需要把 编译SDK版本改成29，如果是26就会出现这样的问题
    compileSdkVersion 29
    buildToolsVersion "29.0.3"

    defaultConfig {
        applicationId "com.example.testtextview"
        minSdkVersion 26
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"








