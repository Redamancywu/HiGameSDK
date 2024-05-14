package com.hi.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Constants {

    public static String TAG = "HiGameSDK";
    public static String HIGAME_SDK_VERSION_BASE = "1.0.0";
    public static String HIGAME_SDK_VERSION_BETA = generateBetaVersion();
    public static String HIGAME_SDK_VERSION = generateVersion();

    private static String generateBetaVersion() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmm", Locale.getDefault()).format(new Date());
        return HIGAME_SDK_VERSION_BASE + "_" + timestamp + "_Beta";
    }

    private static String generateVersion() {
        return HIGAME_SDK_VERSION_BASE;
    }
}
