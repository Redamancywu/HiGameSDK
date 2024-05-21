package com.hi.base.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import com.hi.base.HiPluginManger;
import com.hi.base.utils.Constants;

public class ApplicationHolder {

    private static Application currApplication;

    private static boolean isMainProcess(Context context){
        try{
            return context.getPackageName().equals(getCurrentProcessName(context));
        }catch(Exception e){
            e.printStackTrace();
        }

        return true;

    }

    private static String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }
        return processName;
    }

    public static void onAttachBaseContext(Application app, Context base) {
        if (isMainProcess(base)) {

            currApplication = app;
           // Log.init(base);
            Log.d(Constants.TAG, "begin to load loal params.");
        //    GlobalConfig.getInstance().loadLocalParams(base);
          //  HttpClient.getInstance().init(true);
           // Log.d(Constants.TAG, "curr sdk version:" + GlobalConfig.getInstance().getLocalSDKVersionName());
        }
    }

    public static void onCreate(Application app) {
        if(isMainProcess(app)) {

            //初始化插件
            Log.d(Constants.TAG, "begin to load plugins.");
            try {
              //  HiPluginManger.getInstance().InitPlugin(app);
              //  HiPluginManger.getInstance().InitPlugin(app);
             //   UGPlugins.getInstance().initPlugins(app);
            }catch (Exception e){
                e.printStackTrace();
            }

            // 初始化google play游戏服务组件
           /// GooglePlayGameServiceLogin.getInstance().init(app);
        }
    }

    public static void onConfigurationChanged(Configuration configuration) {

    }

    public static void onTerminate(Application app) {
        if(isMainProcess(app)) {
           // Log.destory();
          //  HttpClient.getInstance().destroy();
        }
    }

    public static Application getCurrApplication() {

        return currApplication;
    }

}
