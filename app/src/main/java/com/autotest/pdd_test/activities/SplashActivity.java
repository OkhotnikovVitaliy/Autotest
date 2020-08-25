package com.autotest.pdd_test.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.autotest.pdd_test.R;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    TextView skip;
    Intent intent;
    boolean isPressed;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              if(!isPressed){
                intent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }}
        }, SPLASH_DISPLAY_LENGTH);
        skip = (TextView) findViewById(R.id.skip);
        skip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.skip) {
            isPressed = true;
            intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}