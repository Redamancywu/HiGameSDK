package com.hi.base.plugin.itf;

import android.app.Activity;

import com.hi.base.plugin.IPlugin;
import com.hi.base.plugin.pay.IPayCallBack;
import com.hi.base.plugin.pay.PayParams;

public interface IPay extends IPlugin {
    String type="pay";
    void Pay(Activity activity,PayParams params, IPayCallBack callback);

}
