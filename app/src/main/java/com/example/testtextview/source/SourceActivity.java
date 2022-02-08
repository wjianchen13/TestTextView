package com.example.testtextview.source;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.testtextview.R;
import com.example.testtextview.string.SpannableString;
import com.example.testtextview.string.SpannableStringBuilder;
import com.example.testtextview.textview.LinkMovementMethod;
import com.example.testtextview.textview.TextView;

public class SourceActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        tv = findViewById(R.id.tv_test);
        tv.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        test();
    }
    
    private void test() {
        String tip1 = "test1";
        String tip2 = "test2";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
//        SpannableString spanStr1 = new SpannableString(tip1);
//        spanStr1.setSpan(createDinoNolineClickableSpan(true, ContextCompat.getColor(this, R.color.colorPrimaryDark), this)
//                , spanStr1.length(), spanStr1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableStringBuilder.append(spanStr1);
//        spannableStringBuilder.append(" ");
        SpannableString spanStr2 = new SpannableString(tip2);
        spanStr2.setSpan(createDinoNolineClickableSpan(true, Color.parseColor("#FF2A7A"), this)
                , 0, spanStr2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(spanStr2);
        ImageSpan imageSpan = new MyIm(this, R.drawable.ic_launcher);
        spannableStringBuilder.setSpan(imageSpan, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannableStringBuilder);
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
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
    }
}