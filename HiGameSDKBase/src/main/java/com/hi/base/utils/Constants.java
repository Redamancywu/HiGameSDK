package com.hi.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Constants {

    public static String TAG = "HiGameSDK";
    public static String HIGAME_SDK_VERSION_BASE = "1.0.0";
    public static String HIGAME_SDK_VERSION_BETA = generateBetaVersion();
    public static String HIGAME_SDK_VERSION = generateVersion();

    public final static int CODE_LOAD_FAILED = 1;           //加载失败
    public final static int CODE_SHOW_FAILED = 2;           //展示失败

    private static String generateBetaVersion() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault()).format(new Date());
        return HIGAME_SDK_VERSION_BASE + "_" + timestamp + "_Beta";
    }

    private static String generateVersion() {
        return HIGAME_SDK_VERSION_BASE;
    }
}
