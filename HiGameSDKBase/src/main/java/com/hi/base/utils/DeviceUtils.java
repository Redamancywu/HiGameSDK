package com.hi.base.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.UUID;

public class DeviceUtils {
	
	protected static String uuid;

	/**
	 * 获取设备号
	 * 这里首次获取的时候，生成一个uuid存本地，当用户卸载app之后， uuid失效
	 * 因为google现在严格限制设备信息的采集和硬件指标的获取，如果使用硬件指标，会影响上架和游戏推荐
	 * @param context
	 * @return
	 */
	public static String getDeviceID(Context context){

		if( TextUtils.isEmpty(uuid)) {

			uuid = StoreUtils.getString(context, "ug_device_id");

			if (TextUtils.isEmpty(uuid)) {
				uuid = UUID.randomUUID().toString().replace("-", "");
				StoreUtils.putString(context, "ug_device_id", uuid);
			}
		}

		return uuid;
	}

	public static String getModel(Context context) {

		return Build.MODEL;
	}
	
	//获取mac地址
	public static String getMacAddress(Context context) {
		//海外渠道，暂时都返回不支持
		return "un_support";

	  }

	
	/**
	 * 获取屏幕分辨率
	 * @param activity
	 * @return
	 */
	public static String getScreenDpi(Activity activity){
		
		DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        return dm.widthPixels + "×" + dm.heightPixels;
		
	}

}
