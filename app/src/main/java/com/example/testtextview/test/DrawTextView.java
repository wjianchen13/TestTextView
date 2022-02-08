package com.example.testtextview.test;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.testtextview.source.MyIm;
import com.example.testtextview.source.NoLineClickDinoSpan;
import com.example.testtextview.R;
import com.example.testtextview.dynamiclayout.DynamicLayout;
import com.example.testtextview.dynamiclayout.Layout;
import com.example.testtextview.textview.LinkMovementMethod;
import com.example.testtextview.textview.MovementMethod;

/**
 * name: Draw Text
 * desc: canvas绘制文字
 * author:
 * date: 2018-06-26 16:00
 * remark:
 */
public class DrawTextView extends View implements View.OnClickListener{
    
    private Context mContext;
    
    /**
     * 字体颜色
     */
    private int mTextColor;    

    /**
     * 字体大小
     */
    private float mTextSize;

    private MovementMethod mMovement;

    private TextPaint textPaint;

    private SpannableString spannableString;

    private String text = "11分";

    private StyleSpan styleSpan;

    private RelativeSizeSpan relativeSizeSpan;

    private DynamicLayout dynamicLayout;

    private CharSequence mText;

    public DrawTextView(Context context) {
        super(context);
    }
    
    public DrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initAttrs(context, attrs);
        init();
    }

    public DrawTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取属性
     * @param context
     * @param attributeSet
     */
    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.DrawTextView);
        mTextColor = typedArray.getColor(R.styleable.DrawTextView_drawTextColor, 0);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.DrawTextView_drawTextSize, dp2px(14));
        typedArray.recycle();
    }

    /**
     * 初始化
     */
    private void init() {
        initPaint();
        relativeSizeSpan = new RelativeSizeSpan(0.6f);
        styleSpan = new StyleSpan(android.graphics.Typeface.BOLD);
        mMovement = LinkMovementMethod.getInstance();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint.setColor(mTextColor);
        textPaint.setTextSize(sp2px(22));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        String str = "ABCDEFG";
//        canvas.drawText(str, 10, 10, textPaint);
        drawTitleText(canvas);
    }

    private void drawTitleText(Canvas canvas) {
        canvas.save();
        textPaint.setTextSize(mTextSize);
        float textWidth = textPaint.measureText(text);   // 文字宽度
        float textHeight = -textPaint.ascent() + textPaint.descent();  // 文字高度
        canvas.translate(200 - textWidth * 2 / 5f, 200 - textHeight * 2 / 3f);
        spannableString = SpannableString.valueOf(text);
        spannableString.setSpan(styleSpan, 0, text.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(relativeSizeSpan, text.length() - 1, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        dynamicLayout = new DynamicLayout(getString(), textPaint, getWidth(), Layout.Alignment.ALIGN_NORMAL, 0, 0, false);
        dynamicLayout.draw(canvas);
        canvas.restore();
    }
    
    private SpannableStringBuilder getString() {
        String tip1 = "test1";
        String tip2 = "test2";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString spanStr1 = new SpannableString(tip1);
        spanStr1.setSpan(createDinoNolineClickableSpan(true, ContextCompat.getColor(mContext, R.color.colorPrimaryDark), this)
                , spanStr1.length(), spanStr1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(spanStr1);
        spannableStringBuilder.append(" ");
        SpannableString spanStr2 = new SpannableString(tip2);
        spanStr2.setSpan(createDinoNolineClickableSpan(true, Color.parseColor("#FF2A7A"), this)
                , 0, spanStr2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(spanStr2);
        ImageSpan imageSpan = new MyIm(mContext, R.drawable.ic_launcher);
        spannableStringBuilder.setSpan(imageSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    public ClickableSpan createDinoNolineClickableSpan(boolean showUnderLine, int color, final View.OnClickListener listener) {
        NoLineClickDinoSpan mNoLineClickSpan = new NoLineClickDinoSpan(color, listener) {
            @Override
            public void onClick(View widget) {
                super.onClick(widget);
            }
        };
        mNoLineClickSpan.setShowDinoUnderLine(showUnderLine);
        return mNoLineClickSpan;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(mContext, "test", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getActionMasked();

        final boolean superResult = super.onTouchEvent(event);
        
//        if ((mMovement != null || onCheckIsTextEditor()) && isEnabled()
//                && mText instanceof Spannable && mLayout != null) {
//            boolean handled = false;
//
//            if (mMovement != null) {
//                handled |= mMovement.onTouchEvent(this, (Spannable) mText, event);
//            }
//            
//
//
//            if (handled) {
//                return true;
//            }
//        }
//        Spanned sp = (Spanned) text;
//        Object[] spans = sp.getSpans(start, end, Object.class);

        return superResult;
    }
    
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}
