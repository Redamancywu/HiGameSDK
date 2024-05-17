package com.hi.base.plugin.ad;

import com.hi.base.plugin.IPlugin;

public interface IAd extends IPlugin {
    String type = "ad";

    void setInitializationListener(IAdInitializationListener initializationListener);
}
