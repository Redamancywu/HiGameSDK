package com.hi.ad.max;

import android.content.Context;
import android.util.Log;

import com.applovin.sdk.AppLovinMediationProvider;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.applovin.sdk.AppLovinSdkInitializationConfiguration;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.ad.AdApter;
import com.hi.base.utils.Constants;

import java.lang.ref.PhantomReference;

public class MaxAd extends AdApter {
    private HiGameConfig config;
    private Context mContext;
    private String SDK_KEY;
    @Override
    public void init(Context context, HiGameConfig config) {
        super.init(context, config);
        this.config=config;
        this.mContext=context;
        if (config.contains("SDK-key")){
            SDK_KEY=config.getString("SDK-key");
        }
        AppLovinSdkInitializationConfiguration initConfig=AppLovinSdkInitializationConfiguration.builder(SDK_KEY,context).setMediationProvider(AppLovinMediationProvider.MAX).build();
        AppLovinSdk.getInstance(context).initialize(initConfig, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(AppLovinSdkConfiguration appLovinSdkConfiguration) {
                if (initializationListener!=null){
                    Log.d(Constants.TAG,"MaxAd onSdkInitialized");
                    initializationListener.onInitSuccess();
                }

            }
        });
    }
}
