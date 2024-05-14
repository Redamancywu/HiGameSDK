package com.hi.base.utils;

import android.util.Log;

public class ClassUtils {

    /**
     * 无参反射实例化
     * @param className
     * @return
     */
    public static Object doNoArgsInstance(String className) {

        Class localClass = null;

        try {

            localClass = Class.forName(className);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e( Constants.TAG,"doInstance failed for class:" + className);
            return null;
        }

        try {
            //以默认构造函数再次尝试实例化
            return localClass.getDeclaredConstructor().newInstance();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

}
