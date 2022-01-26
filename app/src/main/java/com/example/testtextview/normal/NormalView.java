package com.example.testtextview.normal;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * name: Draw Text
 * desc: canvas绘制文字
 * author:
 * date: 2018-06-26 16:00
 * remark:
 */
public class NormalView extends View {


    // 1.创建一个画笔
    private Paint mPaint = new Paint();

    // 2.初始化画笔
    private void initPaint() {
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
    }

    public NormalView(Context context) {
        super(context);
    }

    // 3.在构造函数中初始化
    public NormalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public NormalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 第一种
        canvas.drawRect(100,100,800,400,mPaint);

//        // 第二种
//        Rect rect = new Rect(100,100,800,400);
//        canvas.drawRect(rect,mPaint);
//
//        // 第三种
//        RectF rectF = new RectF(100,100,800,400);
//        canvas.drawRect(rectF,mPaint);

    }
}
