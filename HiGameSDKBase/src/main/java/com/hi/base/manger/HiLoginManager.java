package com.hi.base.manger;


import android.app.Activity;
import android.content.Context;

import com.hi.base.data.HiUser;
import com.hi.base.store.UserStore;
import com.hi.base.utils.Constants;
import com.hi.login.facebook.FacebookLogin;
import com.hi.login.google.GoogleLogin;
import com.hi.login.line.LineLogin;
import com.hi.login.twiter.TwitterLogin;
import com.hi.login.vistor.VisitorLogin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HiLoginManager {
    private static HiLoginManager instance;

    public static HiLoginManager getInstance() {
        if (instance==null){
            instance=new HiLoginManager();
        }
        return instance;
    }

    /**
     * 当前选择的登陆类型， 初始值为上次登录的类型，如果上次没有，需要弹出界面选择
     */




    /**
     * SDK登录入口
     * @param context
     */


}
