package com.hi.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class StoreUtils {

	private static SharedPreferences sp;
	
	public static SharedPreferences getSharedPreferences(Context context){
		if(sp == null){
			String pname = context.getPackageName()+"_preferences";
			sp = context.getSharedPreferences(pname, Context.MODE_PRIVATE);
		}
		return sp;
	}

	/**
	 * 从本地存储中读取int值
	 * @param context
	 * @param key  键
	 * @param defaultVal 默认值
	 * @return
	 */
	public static int getInt(Context context, String key, int defaultVal){
		
		return getSharedPreferences(context).getInt(key, 0);
	}

	/**
	 * 向本地存储中存入int值
	 * @param context
	 * @param key 键
	 * @param val 值
	 */
	public static void putInt(Context context, String key, int val){
		Editor edit = getSharedPreferences(context).edit();  
		edit.putInt(key, val);
		edit.commit();	
	}

	/**
	 * 从本地存储中读取boolean值
	 * @param context
	 * @param key  键
	 * @param defaultVal 默认值
	 * @return
	 */
	public static boolean getBoolean(Context context, String key, boolean defaultVal){
		
		return getSharedPreferences(context).getBoolean(key, defaultVal);
		
	}

	/**
	 * 向本地存储中写入boolean值
	 * @param context
	 * @param key 键
	 * @param val 值
	 */
	public static void putBoolean(Context context, String key, boolean val){
		Editor edit = getSharedPreferences(context).edit();  
		edit.putBoolean(key, val);
		edit.commit();		
	}

	/**
	 * 从本地存储中读取string值
	 * @param context
	 * @param key 键
	 * @return
	 */
	public static String getString(Context context, String key){

		return getSharedPreferences(context).getString(key, "");
	}

	/**
	 * 向本地存储中写入string值
	 * @param context
	 * @param key 键
	 * @param value 值
	 */
	public static void putString(Context context, String key, String value){
		
		if(TextUtils.isEmpty(value) || TextUtils.isEmpty(key)){
			return;
		}
		Editor edit = getSharedPreferences(context).edit();  
		edit.putString(key, value);
		edit.commit();
	}

	/**
	 * 删除指定的key
	 * @param context
	 * @param key
	 */
	public static void delete(Context context, String key){

		if(TextUtils.isEmpty(key)){
			return;
		}
		Editor edit = getSharedPreferences(context).edit();
		edit.remove(key);
		edit.commit();
	}
}
