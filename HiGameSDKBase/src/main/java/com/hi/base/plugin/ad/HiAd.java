package com.hi.base.plugin.ad;

import com.hi.base.plugin.IPlugin;

public  interface HiAd extends IPlugin {
    String type="ad";
    void setInitializationListener(HiAdInitializationListener adInitializationListener);
}
