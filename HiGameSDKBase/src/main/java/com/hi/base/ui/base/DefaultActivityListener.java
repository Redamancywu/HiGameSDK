package com.hi.base.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

/**
 * 默认的Activity 监听器实现类
 */
public class DefaultActivityListener implements IActivityListener{

    @Override
    public void onActivityResult(Activity context, int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onStart(Activity context) {

    }

    @Override
    public void onPause(Activity context) {

    }

    @Override
    public void onResume(Activity context) {

    }

    @Override
    public void onNewIntent(Activity context, Intent newIntent) {

    }

    @Override
    public void onStop(Activity context) {

    }

    @Override
    public void onDestroy(Activity context) {

    }

    @Override
    public void onRestart(Activity context) {

    }

    @Override
    public void onBackPressed(Activity context) {

    }

    @Override
    public void onConfigurationChanged(Activity context, Configuration newConfig) {

    }

    @Override
    public void attachBaseContext(Context newBase) {

    }

    @Override
    public void onRequestPermissionResult(Activity context, int requestCode, String[] permissions, int[] grantResults) {

    }
}
