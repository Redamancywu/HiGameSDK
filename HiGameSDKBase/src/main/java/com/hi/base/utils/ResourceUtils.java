package com.hi.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Properties;

/**
 * 资源反射获取方式
 *
 */
public class ResourceUtils {
	
	static Handler mainThreadHandler;

	/**
	 * 在UI线程中执行
	 * @param task 执行任务
	 */
	public static void runOnUIThread(Runnable task){
		if(mainThreadHandler == null){
			mainThreadHandler = new Handler(Looper.getMainLooper());
		}
		mainThreadHandler.post(task);
	}

	/**
	 * 显示toast 提示框
	 * @param context 当前activity
	 * @param resID 提示资源id
	 */
	public static void showTip(Context context, String resID){
		if(resID != null && !resID.startsWith("R.string")){
			showTipStr(context, resID);
			return;
		}
		Toast.makeText(context, ResourceUtils.getString(context,resID), Toast.LENGTH_SHORT).show();
	}

	/**
	 * 显示toast提示框
	 * @param context 当前activity
	 * @param msg 提示语句
	 */
	public static void showTipStr(Context context, String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 获取drawable资源
	 * @param context
	 * @param resID 资源id
	 * @return
	 */
	public static Drawable getDrawable(Context context, String resID){
		
		return context.getResources().getDrawable(getResourceID(context, resID));
	}

	/**
	 * 获取string资源
	 * @param context
	 * @param resID 资源id
	 * @return
	 */
	public static String getString(Context context, String resID){
		
		return context.getResources().getString(getResourceID(context, resID));
	}

	/**
	 * get color
	 * @param context
	 * @param resID
	 * @return
	 */
	public static int getColor(Context context, String resID) {

		if(Build.VERSION.SDK_INT >= 23) {
			return context.getColor(getResourceID(context, resID));
		} else {
			return context.getResources().getColor(getResourceID(context, resID));
		}


	}

	/**
	 * 获取view资源
	 * @param context
	 * @param resID 资源id
	 * @return
	 */
	public static View getView(Activity context, String resID){
		
		return context.findViewById(getResourceID(context, resID));
	}

	/**
	 * 在父类中获取view资源
	 * @param view 父控件
	 * @param resID 资源id
	 * @return
	 */
	public static View getViewByParent(View view, String resID){
		
		return view.findViewById(getResourceID(view.getContext(), resID));
	}

	/**
	 * 在window对象中获取view资源
	 * @param view window 对象
	 * @param resID 资源id
	 * @return
	 */
	public static View getViewByWindow(Window view, String resID){
		
		return view.findViewById(getResourceID(view.getContext(), resID));
	}

	/**
	 * 获取资源id
	 * @param context 当前activity
	 * @param paramString 资源id
	 * @return
	 */
	public static int getResourceID(Context context, String paramString){
		try{
			if(paramString != null){
				String[] splits = paramString.split("\\.", 3);
				if(splits.length == 3){
					return context.getResources().getIdentifier(splits[2], splits[1], context.getPackageName());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		
		return 0;
	}

	/**
	 * 获取raw文件
	 * @param context
	 * @param fileName 文件名称
	 * @return
	 * @throws NotFoundException
	 * @throws IOException
	 */
	public static Properties getProperties(Context context, String fileName) throws NotFoundException, IOException{
		int resourceId = getResourceID(context, fileName); 
		Properties props = new Properties();
		props.load(context.getResources().openRawResource(resourceId));
		return props;
	}
	
	/**
	 * 获取资源ID
	 * 一般只用该方法获取styleable类型的资源id
	 * @param context
	 * @param name 资源id
	 * @return
	 */
	public static int getStyleableResourceID(Context context, String name){

		String[] splits = name.split("\\.", 3);
		if(splits.length == 3){
			Object obj = getResourceId(context, splits[2], splits[1]);
			
			return obj == null ? 0 : (Integer)obj;
		}

		return 0;
	}
	
	/***
	 * 获取资源ID数组
	 * 一般只用该方法获取styleable类型的资源数组
	 * @param context
	 * @param name 资源id
	 * @return
	 */
	public static int[] getStyleableResourceIDArray(Context context, String name){
		String[] splits = name.split("\\.", 3);
		if(splits.length == 3){
			Object obj = getResourceId(context, splits[2], splits[1]);
			
			return obj == null ? new int[0] :(int[])obj;
		}
		
		return new int[0];
	}
	
	/**
	 * 纯粹反射获取资源id，比如getResource无法获取styleable类型的资源id和数组。采用这种方式获取
	 * @param context
	 * @param name 名称
	 * @param type 类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Object getResourceId(Context context, String name, String type){
		String className = context.getPackageName()+".R";
		try{
			
			Class cls = Class.forName(className);
			for(Class child : cls.getClasses()){
				String sname = child.getSimpleName();
				if(sname.equals(type)){
					for(Field f : child.getFields()){
						String fname = f.getName();
						if(fname.equals(name)){
							return f.get(null);
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 获取当前本地首选语言环境
	 * @return
	 */
	public static String getCurrLanguage() {
		Locale locale;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			locale = Resources.getSystem().getConfiguration().getLocales().get(0);
		} else {
			//noinspection deprecation
			locale = Resources.getSystem().getConfiguration().locale;
		}

		return locale.toLanguageTag();
	}
	
}
