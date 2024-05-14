package com.hi.base.plugin.ad;

public interface HiAdInitializationListener {
    void onInitSuccess();

    void onInitFailed(int code, String msg);
}
