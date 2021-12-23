package com.example.testtextview.test1;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testtextview.R;
import com.example.testtextview.textview.TextView;

public class Test1Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
    }
}