package com.hi.base.plugin.analytics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hi.base.data.HiOrder;
import com.hi.base.data.HiRoleData;
import com.hi.base.plugin.HiGameConfig;

import java.util.Map;

public  class IBaseAnalytics implements IAnalytics{
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
    public void onInitBegin() {

    }

    @Override
    public void onInitSuc() {

    }

    @Override
    public void onLoginBegin() {

    }

    @Override
    public void onLogin() {

    }

    @Override
    public void onRegister(int regType) {

    }

    @Override
    public void onPurchaseBegin(HiOrder order) {

    }

    @Override
    public void onPurchase(HiOrder order) {

    }

    @Override
    public void onCreateRole(HiRoleData role) {

    }

    @Override
    public void onEnterGame(HiRoleData role) {

    }

    @Override
    public void onLevelup(HiRoleData role) {

    }

    @Override
    public void onCompleteTutorial(int tutorialID, String content) {

    }

    @Override
    public void onCustomEvent(String eventName, Map<String, Object> params) {

    }
}
