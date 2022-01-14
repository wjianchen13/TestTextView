#从源码复制出来的TextView源码版本是26 android 8.0

# 源码查看地址
http://androidxref.com/


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








