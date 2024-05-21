package com.hi.base.model;

public enum HiAdType {
    Splash("splash"),
    Banner("banner"),
    Inters("inters"),
    Reward("reward"),
    Native("native");
    private String adType;
    private HiAdType(String v) {
        this.adType = v;
    }
    public String getAdType() {
        return this.adType;
    }

}
