package com.hi.base.plugin.ad.inters;

public abstract class InterstitialAdAdapter implements IInterstitialAd {
    protected IInterstitialAdListener adListener;

    public void setAdListener(IInterstitialAdListener adListener) {
        this.adListener = adListener;
    }

    public void clearAdListener() {
        this.adListener = null;
    }
}
