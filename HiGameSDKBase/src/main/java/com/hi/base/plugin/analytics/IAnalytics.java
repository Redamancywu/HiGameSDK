package com.hi.base.plugin.analytics;

import com.hi.base.data.HiOrder;
import com.hi.base.data.HiRoleData;
import com.hi.base.plugin.IPlugin;

import java.util.Map;

public interface IAnalytics extends IPlugin {
    String type = "analytics";
    String SDK_KEY_INIT_BEGIN = "hi_sdk_init_begin";           //SDK初始化开始
    String SDK_KEY_INIT_SUC = "hi_sdk_init_success";           //SDK初始化成功
    String SDK_KEY_LOGIN_BEGIN = "hi_sdk_login_begin";         //SDK登陆开始（登陆界面打开，或者自动登录）
    String SDK_KEY_LOGIN_SUC = "hi_sdk_login_suc";             //SDK登陆成功
    String SDK_KEY_REGISTER_SUC = "hi_sdk_register_suc";       //SDK注册成功
    String SDK_KEY_ROLE_CREATE = "hi_sdk_role_create";        //创建角色成功
    String SDK_KEY_ENTER_GAME = "hi_sdk_game_enter";           //进入游戏
    String SDK_KEY_LEVEL_UP = "hi_sdk_level_up";               //角色等级升级
    String SDK_KEY_PAY_BEGIN = "hi_sdk_pay_begin";             //支付开始
    String SDK_KEY_PAY_SUC = "hi_sdk_pay_suc";               //支付成功
    /**
     * 自定义事件： SDK初始化开始
     */
    void onInitBegin();

    /**
     * 自定义事件： SDK初始化成功
     */
    void onInitSuc();

    /**
     * 自定义事件： SDK登陆开始
     */
    void onLoginBegin();

    /**
     * 登陆成功的时候 上报
     * af_login
     */
    void onLogin();

    /**
     * 注册成功的时候 上报
     * af_complete_registration
     */
    void onRegister(int regType);

    /**
     * 自定义事件， 开始购买（SDK下单成功）
     * price：分为单位
     */
    void onPurchaseBegin(HiOrder order);

    /**
     * 购买成功的时候，调用
     * af_purchase
     * price: 分为单位
     */
    void onPurchase(HiOrder order);



    /**
     * 自定义事件， 创建角色成功
     * @param role
     */
    void onCreateRole(HiRoleData role);

    /**
     * 自定义事件， 进入游戏成功
     */
    void onEnterGame(HiRoleData role);

    /**
     * 角色等级 升级的时候，调用
     * af_level_achieved
     */
    void onLevelup(HiRoleData role);

    /**
     * 完成新手教程的时候 执行
     * af_tutorial_completion
     * @param tutorialID
     * @param content
     */
    void onCompleteTutorial(int tutorialID, String content);

    /**
     * 自定义上报
     * @param eventName
     * @param params
     */
    void onCustomEvent(String eventName, Map<String, Object> params);
}
