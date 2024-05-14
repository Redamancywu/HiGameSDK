package com.hi.base.plugin.itf.base;

import android.content.Intent;

import com.hi.base.plugin.ad.HiBaseAd;

public abstract class IBaseAd implements HiBaseAd {

    public String sceneId = "";
    @Override
    public void onCreate() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void setSceneId(String sceneId) {
        this.sceneId=sceneId;
    }
}
