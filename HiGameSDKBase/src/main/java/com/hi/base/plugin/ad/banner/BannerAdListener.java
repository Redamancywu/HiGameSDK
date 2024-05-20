package com.hi.base.plugin.ad.banner;

import android.view.View;

import com.hi.base.plugin.ad.AdSize;
import com.hi.base.plugin.ad.IBaseAd;

/**
 * @ClassName: BannerAdListener
 */
public interface BannerAdListener extends IBaseAd {

    View getBannerView();
    void close();

    void setAdSize(AdSize adSize);

    void setAdListener(IBannerListener adListener);

    // 清理广告监听器
    void clearAdListener();
}
