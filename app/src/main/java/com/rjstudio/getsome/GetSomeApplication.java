package com.rjstudio.getsome;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by r0man on 2017/8/19.
 */

public class GetSomeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
