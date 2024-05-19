package com.hi.adjust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.adjust.sdk.AdjustAttribution;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEvent;
import com.adjust.sdk.LogLevel;
import com.adjust.sdk.OnAttributionChangedListener;
import com.hi.base.app.ApplicationHolder;
import com.hi.base.data.HiOrder;
import com.hi.base.data.HiRoleData;
import com.hi.base.plugin.HiGameConfig;
import com.hi.base.plugin.analytics.IAnalytics;
import com.hi.base.plugin.analytics.IBaseAnalytics;
import com.hi.base.utils.Constants;
import com.hi.base.utils.DeviceUtils;

import java.util.Map;

public class Adjust extends IBaseAnalytics {
    @Override
    public void init(Context context, HiGameConfig config) {
        String appToken = config.getString("app.token");    //应用识别码
        String env = config.getString("app.environment");       //sandbox|production
        String mainProcessName = config.getString("app.main.process");   //如果游戏是多进程，需要设置主进程名称，比如com.ugsdk.demo:main  具体参考：https://help.adjust.com/zh/article/multi-process-apps
        Log.d(Constants.TAG, "adjust init begin app token:" + appToken+";env:"+env);
        String environment = AdjustConfig.ENVIRONMENT_PRODUCTION;
        if (!TextUtils.isEmpty(env) && AdjustConfig.ENVIRONMENT_SANDBOX.equalsIgnoreCase(env)) {
            environment = AdjustConfig.ENVIRONMENT_SANDBOX;
        }
        AdjustConfig adjustConfig = new AdjustConfig(ApplicationHolder.getCurrApplication(), appToken, environment);
        adjustConfig.setLogLevel(LogLevel.DEBUG);
        if (!TextUtils.isEmpty(mainProcessName)) {
            adjustConfig.setProcessName(mainProcessName);
        }
        adjustConfig.setExternalDeviceId(DeviceUtils.getDeviceID(context));  //外部设备号
        adjustConfig.needsCost = true;
        adjustConfig.setOnAttributionChangedListener(new OnAttributionChangedListener() {
            @Override
            public void onAttributionChanged(AdjustAttribution attribution) {
                //归因回传回调
                /**
                 * trackerToken	字符串	设备当前归因跟踪链接的跟踪码
                 * trackerName	字符串	设备当前归因跟踪链接的名称
                 * network	字符串	设备当前归因渠道的名称
                 * campaign	字符串	设备当前归因推广活动的名称
                 * adgroup	字符串	设备当前归因广告组的名称
                 * creative	字符串	设备当前归因素材的名称
                 * clickLabel	字符串	安装被标记的 点击标签
                 * adid	字符串	设备的唯一 Adjust ID
                 * costType	字符串	推广活动定价模型 (如 cpi)
                 * costAmount	数字	安装成本
                 * costCurrency	字符串	成本相关的货币代码。应符合 ISO 4217 标准且包含 3 个字符。
                 */
            }
        });

        com.adjust.sdk.Adjust.onCreate(adjustConfig);
    }
    @Override
    public void onInitBegin() {
        onCustomEvent(SDK_KEY_INIT_BEGIN, null);
    }

    @Override
    public void onInitSuc() {
        onCustomEvent(SDK_KEY_INIT_SUC, null);
    }

    @Override
    public void onLoginBegin() {
        onCustomEvent(SDK_KEY_LOGIN_BEGIN, null);
    }

    @Override
    public void onLogin() {
        onCustomEvent(SDK_KEY_LOGIN_SUC, null);
    }

    @Override
    public void onRegister(int regType) {
        onCustomEvent(SDK_KEY_REGISTER_SUC, null);
    }

    @Override
    public void onPurchaseBegin(HiOrder order) {
        onCustomEvent(SDK_KEY_PAY_BEGIN, null);
    }

    @Override
    public void onPurchase(HiOrder order) {
        super.onPurchase(order);
    }

    @Override
    public void onCreateRole(HiRoleData role) {
        onCustomEvent(SDK_KEY_ROLE_CREATE, null);
    }

    @Override
    public void onEnterGame(HiRoleData role) {
        onCustomEvent(SDK_KEY_ENTER_GAME, null);
    }

    @Override
    public void onLevelup(HiRoleData role) {
        onCustomEvent(SDK_KEY_LEVEL_UP, null);
    }

    @Override
    public void onCompleteTutorial(int tutorialID, String content) {
        super.onCompleteTutorial(tutorialID, content);
    }

    @Override
    public void onCustomEvent(String eventName, Map<String, Object> params) {
        AdjustEvent adjustEvent = new AdjustEvent(eventName);
        com.adjust.sdk.Adjust.trackEvent(adjustEvent);
    }
}
