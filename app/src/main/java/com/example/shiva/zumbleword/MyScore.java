package com.example.shiva.zumbleword;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class MyScore extends AppCompatActivity {
TextView t_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_my_score);
        t_score = (TextView) findViewById(R.id.tv_score);
        Intent intent=getIntent();
        int t=intent.getIntExtra("total",0);
        t_score.setText(""+t);
    }
}
