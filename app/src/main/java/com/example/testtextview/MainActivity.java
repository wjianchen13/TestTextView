package com.example.testtextview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.testtextview.normal.NormalActivity;
import com.example.testtextview.test.DrawTextActivity;
import com.example.testtextview.test1.Test1Activity;
import com.example.testtextview.textview1.TextViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.tv_test);
//        tv.setText("akdkfakjsd");
        
    }

    public void onTextView(View v) {
        startActivity(new Intent(this, TextViewActivity.class));
    }
    
    public void onTest(View v) {
        startActivity(new Intent(this, OtherActivity.class));
    }

    public void onTest1(View v) {
        startActivity(new Intent(this, Test1Activity.class));
    }

    public void onTest2(View v) {
        startActivity(new Intent(this, DrawTextActivity.class));
    }

    public void onTest3(View v) {
        startActivity(new Intent(this, NormalActivity.class));
    }
    
}