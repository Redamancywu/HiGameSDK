package com.hi.base.plugin.ad;

import android.app.Activity;

import com.hi.base.plugin.IPlugin;

public interface IBaseAd extends IPlugin {
    /**
     * 广告是否准备好
     * @return
     */
    boolean isReady();

    /**
     * 加载广告
     */
    void load(Activity context, String posId);

    /**
     * 展示广告
     */
    void show(Activity context);
}
