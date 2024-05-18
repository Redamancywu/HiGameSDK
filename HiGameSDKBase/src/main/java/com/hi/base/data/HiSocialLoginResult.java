package com.hi.base.data;

public class HiSocialLoginResult {
    private int loginType;      //登录方式

    private String accountID;   //社交账号登录返回的唯一账号ID

    private String loginName;   //显示名称

    private String extraData;   //登录验证额外需要的数据信息

    public HiSocialLoginResult() {
    }

    public HiSocialLoginResult(int loginType, String accountID) {
        this.loginType = loginType;
        this.accountID = accountID;
    }

    public HiSocialLoginResult(int loginType, String accountID, String loginName, String extraData) {
        this.loginType = loginType;
        this.accountID = accountID;
        this.loginName = loginName;
        this.extraData = extraData;
    }

    public int getLoginType() {
        return loginType;
    }

    public void setLoginType(int loginType) {
        this.loginType = loginType;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }
}
