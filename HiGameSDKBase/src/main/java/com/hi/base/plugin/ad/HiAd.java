package com.hi.base.plugin.ad;

import com.hi.base.model.HiAdInstType;
import com.hi.base.plugin.IPlugin;

public  interface HiAd extends IPlugin {
    String type="ad";
    void setInitializationListener(HiAdResult adResult);
    HiBaseAd getAdPlugin(HiAdInstType instType);
}
