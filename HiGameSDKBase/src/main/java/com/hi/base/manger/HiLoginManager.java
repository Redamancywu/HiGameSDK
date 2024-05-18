package com.hi.base.manger;


import android.app.Activity;
import android.content.Context;

import com.hi.base.data.HiUser;
import com.hi.base.plugin.login.ILoginComponent;
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
    private Map<Integer, ILoginComponent> socialLoginComponents;            //社交账号登录方式

    /**
     * 当前选择的登陆类型， 初始值为上次登录的类型，如果上次没有，需要弹出界面选择
     */
    private volatile int currLoginType;
    private HiUser currLoginUser;
    private volatile boolean isLogin;
    private long lastTimeStamp = 0;
    public HiLoginManager(){
        socialLoginComponents = new ConcurrentHashMap<>();
    }
    public synchronized void registerLoginComponent(int loginType, ILoginComponent component) {
        if (socialLoginComponents.containsKey(loginType)) {
            socialLoginComponents.remove(loginType);
        }
        socialLoginComponents.put(loginType, component);
    }
    public void init(Context context) {
        registerLoginComponent(Constants.LoginType.Google, (ILoginComponent) new GoogleLogin());
        registerLoginComponent(Constants.LoginType.Facebook, (ILoginComponent) new FacebookLogin());
        registerLoginComponent(Constants.LoginType.Visitor, (ILoginComponent) new VisitorLogin());
        registerLoginComponent(Constants.LoginType.Twitter, (ILoginComponent) new TwitterLogin());
        registerLoginComponent(Constants.LoginType.Line, (ILoginComponent) new LineLogin());

    }
    /**
     * SDK登录入口
     * @param context
     */
    public void login(Activity context) {
        if (this.socialLoginComponents.size() == 0) {
            init(context);
        }
        HiUser lastUser = UserStore.getInstance().getLoginedUser();
//        if (lastUser != null) {
//            AutoLoginDialog dialog = new AutoLoginDialog(context, lastUser);
//            dialog.setChangeAccountListener(new AutoLoginDialog.ChangeAccountListener() {
//                @Override
//                public void onChangeAccount() {
//                    logoutAll(context);
//                    doLogin(context);
//                }
//            });
//            dialog.show();
//        } else {
//            doLogin(context);
//
//        }
    }

}
