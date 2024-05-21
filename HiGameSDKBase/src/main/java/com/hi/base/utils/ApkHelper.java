package com.hi.base.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;


import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ApkHelper {


	/**
	 * 获取版本号
	 * @param context 当前activity
	 * @param packageName 包名
	 * @return 版本号
	 */
	// 获取版本code
	public static long getVersionCode(Context context, String packageName) {

		if (TextUtils.isEmpty(packageName)) {
			return 0;
		}

		try {
			PackageInfo pinfo = context.getPackageManager().getPackageInfo(
					packageName, PackageManager.GET_CONFIGURATIONS);
			if (pinfo != null ) {
				if(Build.VERSION.SDK_INT >= 28){
					return pinfo.getLongVersionCode();
				} else {
					return pinfo.versionCode;
				}
			}

		} catch (NameNotFoundException e) {

			e.printStackTrace();
		}

		return 0;

	}

	/**
	 * 获取版本名称
	 * @param context 当前activity
	 * @param packageName 包名
	 * @return 版本号
	 */
	// 获取版本code
	public static String getVersionName(Context context, String packageName) {

		if (TextUtils.isEmpty(packageName)) {
			return "0.0.0";
		}

		try {
			PackageInfo pinfo = context.getPackageManager().getPackageInfo(
					packageName, PackageManager.GET_CONFIGURATIONS);
			if (pinfo != null) {
				return pinfo.versionName;
			}

		} catch (NameNotFoundException e) {

			e.printStackTrace();
		}

		return "0.0.0";

	}

	public static Map<String, String> getAssetPropConfig(Context context, String assetsPropertiesFile){
		try {
			Properties pro = new Properties();
			pro.load(new InputStreamReader(context.getAssets().open(assetsPropertiesFile), "UTF-8"));

			Map<String, String> result = new HashMap<>();

			for(Map.Entry<Object, Object> entry : pro.entrySet()){
				String keyStr = entry.getKey().toString().trim();
				String keyVal = entry.getValue().toString().trim();
				if(!result.containsKey(keyStr)){
					result.put(keyStr, keyVal);
				}
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new HashMap<>();
	}

	/***
	 * 获取assets目录下文件内容
	 * @param context
	 * @param assetsFile
	 * @return
	 */
	public static String loadAssetFile(Context context, String assetsFile){
		InputStreamReader reader = null;
		BufferedReader br = null;
		try {
			reader = new InputStreamReader(context.getAssets().open(assetsFile));
			br = new BufferedReader(reader);

			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = br.readLine())!= null){
				sb.append(line);
			}

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(Constants.TAG, "getAssetConfigs failed." + assetsFile);
		}finally{
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		return null;

	}

	/**
	 * 检查指定的包名，在设备上是否有安装
	 * 支持一次性查询多个包名，只要其中一个查询到有安装，就返回true
	 * @param context
	 * @param packages
	 * @return
	 */
	public static boolean checkAppInstalled(Context context, String...packages){

		if (context == null || packages == null || packages.length == 0) {
			Log.w(Constants.TAG,"checkAppInstalled failed. context or packages is null");
			return true;
		}
		PackageManager pm=context.getPackageManager();
		ApplicationInfo applicationInfo;

		for (String p : packages) {
			try {
				applicationInfo = pm.getApplicationInfo(p, 0);
				return true;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	/**
	 * 从资源文件中获取 XmlResourceParser 对象
	 * @param context 上下文
	 * @param resourceName 资源名称
	 * @return XmlResourceParser 对象，如果资源不存在则返回 null
	 */
	public static XmlResourceParser getXmlResourceParser(Context context, String resourceName) {
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier(resourceName, "xml", context.getPackageName());
		if (resourceId == 0) {
			Log.e(Constants.TAG, "Resource " + resourceName + " not found");
			return null;
		}
        return resources.getXml(resourceId);
    }
}
