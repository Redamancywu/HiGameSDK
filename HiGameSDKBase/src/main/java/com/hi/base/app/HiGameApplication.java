package com.hi.base.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

public class HiGameApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ApplicationHolder.onAttachBaseContext(this, base);
    }

    public void onCreate() {
        super.onCreate();
        ApplicationHolder.onCreate(this);

    }

    public void onTerminate() {
        super.onTerminate();
        ApplicationHolder.onTerminate(this);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ApplicationHolder.onConfigurationChanged(configuration);
    }
}
