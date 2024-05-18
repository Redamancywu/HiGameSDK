package com.hi.base.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

/**
 * Activity生命周期
 */
public interface IActivityListener {

    void onActivityResult(Activity context, int requestCode, int resultCode, Intent data);
    void onStart(Activity context);
    void onPause(Activity context);
    void onResume(Activity context);
    void onNewIntent(Activity context, Intent newIntent);
    void onStop(Activity context);
    void onDestroy(Activity context);
    void onRestart(Activity context);
    void onBackPressed(Activity context);
    void onConfigurationChanged(Activity context, Configuration newConfig);
    void attachBaseContext(Context newBase) ;
    void onRequestPermissionResult(Activity context, int requestCode, String[] permissions, int[] grantResults);


}
