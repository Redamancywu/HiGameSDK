package com.hi.base.plugin.ad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hi.base.plugin.HiGameConfig;

public  class AdApter implements IAd{
    protected IAdInitializationListener initializationListener;

    @Override
    public void setInitializationListener(IAdInitializationListener initializationListener) {
        this.initializationListener = initializationListener;
    }

    @Override
    public void init(Context context, HiGameConfig config) {

    }

    @Override
    public void onCreate(Activity activity) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onNewIntent(Intent intent) {

    }
}
