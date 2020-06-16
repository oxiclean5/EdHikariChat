package com.example.edhikarchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DP_TIME = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //핸들러 추가
        Handler handler = new Handler();
        //post Delayed 함수
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //spalsh 엑티비티 실행
                startActivity(new Intent(getApplication(),MainActivity.class));
                splash.this.finish();
            }
        },SPLASH_DP_TIME);


    }
}