package com.hi.base.plugin.ad.inters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hi.base.plugin.HiGameConfig;

public  class InterstitialAdAdapter implements IInterstitialAd {
    protected IInterstitialAdListener adListener;

    public void setAdListener(IInterstitialAdListener adListener) {
        this.adListener = adListener;
    }

    @Override
    public void close() {

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

    @Override
    public String getAdId() {
        return null;
    }
}
