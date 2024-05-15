package com.hi.base.model;

public enum HiAdInstType {
    Splash("splash"),
    Banner("banner"),
    NativeBanner("nativeBanner"),
    IntersVideo("intersVideo"),
    NativeInters("nativeInters"),
    Video("video");



    private String instAdType;
    private HiAdInstType(String v) {
        this.instAdType = v;
    }
    public String getAdInstType() {
        return this.instAdType;
    }
}
