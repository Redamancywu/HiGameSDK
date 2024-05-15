package com.hi.base.plugin.itf.base;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.hi.base.plugin.ad.HiBaseAd;

public abstract class HiBBaseAd implements HiBaseAd {

    public String sceneId = "";

    @Override
    public void onCreate(Activity activity) {

    }

    @Override
    public void onStart() {

    }
    @Override
    public void onRestart() {

    }
    @Override
    public void onPause() {

    }
    @Override
    public void onResume() {

    }
    @Override
    public void onStop() {

    }
    @Override
    public void onDestroy() {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }

    @Override
    public void setSceneId(String sId){
        this.sceneId = sId;
    }
}
