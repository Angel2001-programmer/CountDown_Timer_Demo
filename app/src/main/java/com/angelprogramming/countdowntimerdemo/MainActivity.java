package com.angelprogramming.countdowntimerdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    TextView tvTimer;
    Button btnStart;
    MediaPlayer alarm;
    SeekBar customSeekBar;
    CountDownTimer countDownTimer = null;
    Boolean mtimer = false;
    long millisUntilFinished, mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTimer = findViewById(R.id.tvTimer);
        btnStart = findViewById(R.id.btnStart);
        alarm = MediaPlayer.create(this, R.raw.alarm);
        customSeekBar = findViewById(R.id.seekBar);

        if (savedInstanceState != null) {
            millisUntilFinished = savedInstanceState.getLong("millisUntilFinished");
        }

        customSeekBar.setProgress(0);
        customSeekBar.setMax(300);
        customSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress, boolean fromUser) {
                update(progress);
                if (!fromUser) {
                    return;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mtimer != true) {
                    mtimer = true;
                    btnStart.setBackgroundResource(R.drawable.button_design_overlay);
                    btnStart.setText("Stop!");

                    countDownTimer = new CountDownTimer(customSeekBar.getProgress() * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            update((int) millisUntilFinished / 1000);
                        }

                        @Override
                        public void onFinish() {
                            restart();
                        }
                    };
                    countDownTimer.start();
                } else {
                    restart();
                }
            }
        });
    }

    public void restart() {
        mtimer = false;
        tvTimer.setText("Time left: " + "0:00");
        countDownTimer.cancel();
        btnStart.setText("Go!");
        btnStart.setBackgroundResource(R.drawable.button_design);
        customSeekBar.setProgress(0);
        alarm.start();
    }

    public void update(int progress) {
        int minutes = progress / 60;
        int seconds = progress % 60;
        String secondsFinal = "";
        if (seconds <= 9) {
            secondsFinal = "0" + seconds;
        } else {
            secondsFinal = "" + seconds;
        }
        customSeekBar.setProgress(progress);
        tvTimer.setText("Time left: " + minutes + ":" + secondsFinal);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millisUntilFinished", millisUntilFinished);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        millisUntilFinished = savedInstanceState.getLong("millisUntilFinished");

    }
}