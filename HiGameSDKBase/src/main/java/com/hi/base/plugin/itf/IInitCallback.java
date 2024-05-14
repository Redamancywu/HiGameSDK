package com.hi.base.plugin.itf;

public interface IInitCallback {
    //初始化接口回调
    void onInitSuccess();
    void onInitFail(int code, String msg);
}
