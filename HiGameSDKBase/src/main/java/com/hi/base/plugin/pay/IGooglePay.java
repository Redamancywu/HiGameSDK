package com.hi.base.plugin.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.itf.IPay;
import com.hi.base.plugin.pay.IPayCallBack;
import com.hi.base.plugin.pay.PayParams;

public class IGooglePay implements IPay {

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
    public void Pay(Activity activity, PayParams params, IPayCallBack callback) {

    }

    @Override
    public void querySubStatus() {

    }

    @Override
    public void queryProducts() {

    }

    @Override
    public String getAllSubInfo() {
        return null;
    }

    @Override
    public String getProductInfo() {
        return null;
    }
}
