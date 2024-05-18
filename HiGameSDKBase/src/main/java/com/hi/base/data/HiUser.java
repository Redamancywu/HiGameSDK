package com.hi.base.data;

/**
 * 登录之后返回的数据
 */
public class HiUser {

    private String uid;
    private String name;
    private String loginName;
    private String token;

    private int accountType;

    private boolean newAccount;

    public HiUser() {
    }

    public HiUser(String uid, String name, String loginName, String token, int accountType, boolean newAccount) {
        this.uid = uid;
        this.name = name;
        this.loginName = loginName;
        this.token = token;
        this.accountType = accountType;
        this.newAccount = newAccount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public boolean isNewAccount() {
        return newAccount;
    }

    public void setNewAccount(boolean newAccount) {
        this.newAccount = newAccount;
    }
}
