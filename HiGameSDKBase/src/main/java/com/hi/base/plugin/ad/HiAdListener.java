package com.hi.base.plugin.ad;

/**
 * 广告回调监听接口
 */
public interface HiAdListener {

    /**
     * 广告展示失败
     * @param code
     * @param msg
     */
    public void onFailed(int code, String msg);

    /**
     * 广告加载失败
     * @param code
     * @param msg
     */
    public void onLoadFailed(int code, String msg);

    /**
     * 广告加载成功
     */
    public void onLoaded();

    /**
     * 广告展示成功
     */
    public void onShow();

    /**
     * 广告被点击
     */
    public void onClicked();

    /**
     * 广告被关闭
     */
    public void onClosed();

    /**
     * 广告点击跳过
     */
    public void onSkip();

}
