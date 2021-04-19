package com.angelprogramming.countdowntimerdemo;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class Timer{

    TextView tvTimer;
    Button btnStart;
    CountDownTimer timer;
    MediaPlayer alarm;
    Boolean onTrue = false;
    CountDownTimer countDownTimer;

    countDownTimer = new CountDownTimer(30000, 1000) {



        @Override
        public void onFinish() {
            onComplete();
        }
    };

    public void onComplete() {
        onTrue = false;
        tvTimer.setText("Timer:\n" + "00:00");
        timer.cancel();
        btnStart.setText("GO!");
        btnStart.setBackgroundResource(R.drawable.button_design);
        alarm.start();
    }

    public void blah(){

    }

    public void onPlay() {
        onTrue = true;
        btnStart.setText("Stop!");
        btnStart.setBackgroundResource(R.drawable.button_design_overlay);
        timer.start();
    }

}
