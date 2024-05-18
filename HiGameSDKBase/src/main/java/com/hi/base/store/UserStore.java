package com.hi.base.store;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;


import com.hi.base.app.ApplicationHolder;
import com.hi.base.data.HiUser;
import com.hi.base.utils.StoreUtils;

import org.json.JSONObject;

/**
 * 登陆用户信息的相关本地操作
 */
public class UserStore {

    private final String KEY = "hi_user_config";
    private static UserStore instance;

    private UserStore(){}

    public static UserStore getInstance() {
        if(instance == null){
            instance = new UserStore();
        }
        return instance;
    }

    /**
     * 保存登陆的用户信息到本地
     * @param user
     */
    public boolean saveLoginedUser(HiUser user) {

        try{
            JSONObject json = new JSONObject();
            json.put("uid", user.getUid());
            json.put("name", user.getName());
            json.put("loginName", user.getLoginName());
            json.put("token", user.getToken());
            json.put("accountType", user.getAccountType());
            String saveStr = Base64.encodeToString(json.toString().getBytes(), Base64.DEFAULT);
            StoreUtils.putString(ApplicationHolder.getCurrApplication(), KEY, saveStr);
            return true;

        }catch (Exception e){
            Log.e("UserStore:saveLoginedUser failed with exception:", e.getMessage(), e);
            e.printStackTrace();
        }

        return false;

    }


    /**
     * 获取上一次登陆的用户信息
     */
    public HiUser getLoginedUser() {

        try {

            String str = StoreUtils.getString(ApplicationHolder.getCurrApplication(), KEY);
            if(TextUtils.isEmpty(str)){
                return null;
            }

            String jsonStr = Base64.encodeToString(str.toString().getBytes(), Base64.DEFAULT);
            JSONObject json = new JSONObject(jsonStr);
            HiUser user = new HiUser();
            user.setUid(json.getString("uid"));
            user.setName(json.getString("name"));
            user.setLoginName(json.optString("loginName"));
            user.setToken(json.getString("token"));
            user.setAccountType(json.getInt("accountType"));
            return user;

        }catch (Exception e){
            Log.e("UserStore:getLoginedUser failed with exception:", e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }

    public void deleteLoginedUser() {
        StoreUtils.delete(ApplicationHolder.getCurrApplication(), KEY);
    }
}
