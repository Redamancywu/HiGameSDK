package com.hi.base.plugin.ad;

public interface IAdInitializationListener {
    void onInitSuccess();

    void onInitFailed(int code, String msg);
}
