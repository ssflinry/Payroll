package com.example.application.utilities;

import android.os.Handler;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LiveClock {
    private TextView clockText;
    private Handler handler;
    private final int delayMillis = 1000;

    public LiveClock(TextView clockText) {
        this.clockText = clockText;
        handler = new Handler();
    }

    public void start() {
        updateLiveClock();
    }

    public void stop() {
        handler.removeCallbacksAndMessages(null);
    }

    private void updateLiveClock() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
                String currentTime = sdf.format(new Date());

                clockText.setText(currentTime);

                updateLiveClock();
            }
        }, delayMillis);
    }
}
