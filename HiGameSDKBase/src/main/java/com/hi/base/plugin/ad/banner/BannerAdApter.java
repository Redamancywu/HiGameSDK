package com.hi.base.plugin.ad.banner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.AdSize;
import com.hi.base.plugin.ad.IAdListener;

public  class BannerAdApter implements BannerAdListener {
    protected IBannerListener BannerAdListener;
    protected AdSize adSize;

    @Override
    public View getBannerView() {
        return null;
    }

    @Override
    public void setAdSize(AdSize adSize) {
        this.adSize = adSize;
    }

    @Override
    public void setAdListener(IBannerListener adListener) {
        this.BannerAdListener = adListener;
    }

    @Override
    public void clearAdListener() {
        this.BannerAdListener=null;
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
