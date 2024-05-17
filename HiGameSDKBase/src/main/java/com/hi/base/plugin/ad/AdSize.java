package com.hi.base.plugin.ad;

public class AdSize {
    private int width;
    private int height;
    public static final AdSize BANNER_SIZE = new AdSize(320, 50);
    public static final AdSize FULL_BANNER_SIZE = new AdSize(468, 60);
    public static final AdSize LARGE_BANNER_SIZE = new AdSize(320, 100);

    public AdSize(){

    }

    public AdSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
