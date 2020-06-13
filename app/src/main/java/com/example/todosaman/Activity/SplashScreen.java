package com.example.todosaman.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todosaman.R;

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_TIMEOUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent  = new Intent(SplashScreen.this, ActivityLogin.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIMEOUT);

    }
}

