package com.hi.ad.max;

import android.app.Activity;
import android.content.Context;

import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.inters.IInterstitialAdListener;
import com.hi.base.plugin.ad.inters.InterstitialAdAdapter;

public class MaxNativeInterstitialAds extends InterstitialAdAdapter {
    @Override
    public void init(Context context, HiGameConfig config) {
        super.init(context, config);
    }

    @Override
    public void show(Activity context) {
        super.show(context);
    }

    @Override
    public void load(Activity context, String posId) {
        super.load(context, posId);
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void setAdListener(IInterstitialAdListener adListener) {
        super.setAdListener(adListener);
    }

}
