package com.example.fake_real;

import android.app.Application;
import android.os.SystemClock;

public class MyAPP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(3000);
    }
}
