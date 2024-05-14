package com.hi.base.plugin.ad;

import com.hi.base.plugin.IPlugin;

public interface HiBaseAd extends IPlugin {
    /**
     * 广告是否准备好
     */
    boolean isReady();
    /**
     * 加载广告
     */
    void load(String posId);

    /**
     * 展示广告
     */
    void show();
    /**
     * 关闭广告
     */
    void close();

    /**
     * 设置场景ID
     * @param sceneId
     */
    void setSceneId(String sceneId);
}
