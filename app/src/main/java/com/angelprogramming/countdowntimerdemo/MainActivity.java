package com.angelprogramming.countdowntimerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView tvTimer;
    Button btnStart;
    MediaPlayer alarm;
    SeekBar customSeekBar;
    CountDownTimer countDownTimer;
    Boolean timer = true;
    int progresschangedvalue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTimer = findViewById(R.id.tvTimer);
        btnStart = findViewById(R.id.btnStart);
        alarm = MediaPlayer.create(this, R.raw.alarm);
        customSeekBar = findViewById(R.id.seekBar);

        customSeekBar.setMax(3000);
        customSeekBar.setProgress(30);
        customSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer == true) {
                    startTimer();

                    countDownTimer = new CountDownTimer(customSeekBar.getProgress() * 1000 + 100, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            updateTimer((int) millisUntilFinished / 1000);
                            Log.i("Countdown", Long.toString(millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {
                            onComplete();
                        }
                    }.start();
                } else {
                    onComplete();
                }
            }
        });
    }

    public void startTimer() {
        timer = false;
        btnStart.setText("Stop!");
        btnStart.setBackgroundResource(R.drawable.button_design_overlay);
    }

    public void onComplete() {
        tvTimer.setText("Time left:\n" + "0:30");
        btnStart.setText("Start");
        btnStart.setBackgroundResource(R.drawable.button_design);
        timer = true;
        customSeekBar.setProgress(30);
        alarm.start();
        countDownTimer.cancel();
        Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show();
    }

    public void updateTimer(int secondLeft) {
        int minutes = secondLeft / 60;
        int seconds = secondLeft - minutes * 60;
        String secondStr = Integer.toString(seconds);

        if (seconds <= 9) {
            secondStr = "0" + secondStr;
        }

        if (minutes == 0 && seconds == 0) {
            countDownTimer.cancel();
            Toast.makeText(this, "choose a positive number", Toast.LENGTH_LONG).show();
        }
        tvTimer.setText("Time left:\n" + Integer.toString(minutes) + ":" + secondStr);
        //plz give me strength to code
    }
}