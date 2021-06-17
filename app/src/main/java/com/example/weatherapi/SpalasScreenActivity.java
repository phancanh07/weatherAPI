package com.example.weatherapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class SpalasScreenActivity extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalas_screen);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        CountDownTimer countDownTimer = new CountDownTimer(2000, 200) {
            @Override
            public void onTick(long millisUntilFinished) {
                int bandau = progressBar.getProgress();
                progressBar.setProgress(bandau + 10);
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        };
        countDownTimer.start();
    }
}
