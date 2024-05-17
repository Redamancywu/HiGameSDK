package com.hi.base.plugin.ad.banner;

import com.hi.base.plugin.ad.AdSize;
import com.hi.base.plugin.ad.IAdListener;

public abstract class BannerAdApter implements BannerAdListener {
    protected IAdListener BannerAdListener;
    protected AdSize adSize;

    @Override
    public void setAdSize(AdSize adSize) {
        this.adSize = adSize;
    }

    @Override
    public void setAdListener(IBannerListener adListener) {
        this.BannerAdListener = adListener;
    }

    @Override
    public void clearAdListener() {
        this.BannerAdListener=null;
    }

}
