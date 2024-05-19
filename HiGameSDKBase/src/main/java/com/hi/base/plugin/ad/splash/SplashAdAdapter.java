package com.hi.base.plugin.ad.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hi.base.plugin.HiGameConfig;

public  class SplashAdAdapter implements ISplashAd {

    protected ISplashAdListener adListener;

    public void setAdListener(ISplashAdListener adListener) {
        this.adListener = adListener;
    }

    public void clearAdListener() {
        this.adListener = null;
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

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void load(Activity context, String posId) {

    }

    @Override
    public void show(Activity context) {

    }
}
