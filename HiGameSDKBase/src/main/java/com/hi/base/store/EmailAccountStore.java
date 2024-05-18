package com.hi.base.store;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.hi.base.app.ApplicationHolder;
import com.hi.base.data.HiEmailAccount;
import com.hi.base.utils.StoreUtils;

import org.json.JSONObject;

/**
 * 邮箱账号相关的存储操作
 */
public class EmailAccountStore {
    private final String KEY = "hi_email_config";

    private static EmailAccountStore instance;

    private EmailAccountStore(){}

    public static EmailAccountStore getInstance() {
        if(instance == null){
            instance = new EmailAccountStore();
        }
        return instance;
    }

    /**
     * 保存登陆的邮箱账号和密码信息
     */
    public boolean saveEmailAccount(HiEmailAccount account) {

        try {

            JSONObject json = new JSONObject();
            json.put("email", account.getEmail());
            json.put("password", account.getPassword());

            String str = Base64.encodeToString(json.toString().getBytes(), Base64.DEFAULT);

            StoreUtils.putString(ApplicationHolder.getCurrApplication(), KEY, str);

            return true;

        }catch (Exception e){
            Log.e("EmailAccountStore:saveEmailAccount failed with exception:", e.getMessage(), e);
            e.printStackTrace();
        }

        return false;
    }


    /**
     * 获取上次登录的邮箱账号和密码
     * @return
     */
    public HiEmailAccount getEmailAccount() {

        try {

            String str = StoreUtils.getString(ApplicationHolder.getCurrApplication(), KEY);
            if(TextUtils.isEmpty(str)){
                return null;
            }
            String jsonStr = Base64.encodeToString(str.toString().getBytes(), Base64.DEFAULT);
            JSONObject json = new JSONObject(jsonStr);
            return new HiEmailAccount(json.getString("email"), json.getString("password"));

        }catch (Exception e){
            Log.e("EmailAccountStore:getEmailAccount failed with exception:", e.getMessage(), e);
            e.printStackTrace();
        }

        return null;
    }
}
