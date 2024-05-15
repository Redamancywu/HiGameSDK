package com.hi.base.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;

public interface IPlugin {
    void init(Context context,HiGameConfig config);
    void onCreate(Activity activity);

    void onStart();

    void onStop();

    void onRestart();

    void onResume();

    void onPause();

    void onDestroy();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onNewIntent(Intent intent);


}
