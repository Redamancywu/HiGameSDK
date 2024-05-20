package com.hi.base.plugin.login;

import java.lang.ref.PhantomReference;

public enum LoginType {
    GOOGLE("GOOGLE"),

    FACEBOOK("FACEBOOK"),

    TWITTER("TWITTER"),
    LINE("LINE"),

    KAKAO("KAKAO"),

    NAVER("NAVER"),
    VISITOR("VISITOR");
    public String LoginType;

    private LoginType(String LoginType) {
        this.LoginType = LoginType;
    }

    public String getLoginType() {
        return this.LoginType;
    }


}
