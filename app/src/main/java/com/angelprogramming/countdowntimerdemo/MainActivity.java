package com.angelprogramming.countdowntimerdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    //https://codinginflow.com/tutorials/android/countdowntimer/part-3-run-in-background
    TextView tvTimer;
    Button btnStart;
    MediaPlayer alarm;
    SeekBar customSeekBar;
    CountDownTimer countDownTimer = null;
    Boolean mtimer = true;

    static final long START_TIME_IN_MILES = 600000;
    long mEndTime;
    long millisUntilFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTimer = findViewById(R.id.tvTimer);
        btnStart = findViewById(R.id.btnStart);
        alarm = MediaPlayer.create(this, R.raw.alarm);
        customSeekBar = findViewById(R.id.seekBar);

        customSeekBar.setProgress(0);
        customSeekBar.setMax(300);
        customSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(final SeekBar seekBar, final int progress, boolean fromUser) {
                update(progress);
                if (!fromUser)
                    return;
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

    public void startTimer() {
        mEndTime = System.currentTimeMillis() + customSeekBar.getProgress();

        countDownTimer = new CountDownTimer(customSeekBar.getProgress() * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                update((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                mtimer = false;
                buttons();
            }
        }.start();

        buttons();
        mtimer = true;
    }

    public void buttons() {
        startTimer();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mtimer == true) {
                    btnStart.setText("Stop!");
                    btnStart.setBackgroundResource(R.drawable.button_design_overlay);
                    alarm.stop();

                } else {
                    btnStart.setText("Go!");
                    btnStart.setBackgroundResource(R.drawable.button_design);
                    customSeekBar.setProgress(0);
                    tvTimer.setText("0:00");
                    alarm.start();
                }
            }
        });
    }
}


//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//
//        editor.putLong("millisUntilFinished", millisUntilFinished);
//        editor.putBoolean("timerRunnning", mtimer);
//        editor.putLong("endTime", mEndTime);
//
//        editor.apply();
//
//        if(countDownTimer != null){
//            countDownTimer.cancel();
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
//        millisUntilFinished = preferences.getLong("millisUntilFinished", START_TIME_IN_MILES);
//        mtimer = preferences.getBoolean("timerRunnning", false);
//
//        buttons();
//
//        if(mtimer) {
//           mEndTime = preferences.getLong("endTime", 0);
//           millisUntilFinished = mEndTime - System.currentTimeMillis();
//
//           if(millisUntilFinished < 0){
//               millisUntilFinished = 0;
//               mtimer = false;
//               buttons();
//           } else {
//               startTimer();
//           }
//
//        }
