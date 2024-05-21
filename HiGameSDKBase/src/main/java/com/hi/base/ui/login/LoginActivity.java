package com.hi.base.ui.login;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.hi.base.HiGameListener;
import com.hi.base.R;

import com.hi.base.data.HiOrder;
import com.hi.base.data.HiProduct;
import com.hi.base.data.HiUser;
import com.hi.base.manger.HiLoginManager;
import com.hi.base.plugin.login.LoginType;
import com.hi.base.utils.Constants;

import java.util.List;

public class LoginActivity extends Activity implements HiGameListener {
    private View loginAsVisitorView, loginWithGoogleView, loginWithFacebookView, loginWithLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
//        setContentView(activityLoginBinding.getRoot());
        loginAsVisitorView = findViewById(R.id.login_as_visitor);
        loginWithGoogleView = findViewById(R.id.login_with_google);
        loginWithFacebookView = findViewById(R.id.login_with_facebook);
        loginWithLine = findViewById(R.id.login_with_line);
        if (loginAsVisitorView != null && loginWithGoogleView != null && loginWithFacebookView != null && loginWithLine != null) {
            setScreenOrientation(); // 动态设置屏幕方向
            setupClickListeners();
        }

    }

    private void setScreenOrientation() {
        int screenOrientation = getScreenOrientationFromManifest();
        if (screenOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else if (screenOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        } else {
            // 默认行为，如果未设置或设置无效
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    private int getScreenOrientationFromManifest() {
        try {
            ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), 0);
            return activityInfo.screenOrientation;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(Constants.TAG, "Failed to load screen orientation from manifest", e);
        }
        return ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED; // 默认值
    }

    private void setupClickListeners() {
        loginWithGoogleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiLoginManager.getInstance().login(LoginType.GOOGLE);
            }
        });

        loginWithFacebookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiLoginManager.getInstance().login(LoginType.FACEBOOK);
            }
        });

       loginWithLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiLoginManager.getInstance().login(LoginType.TWITTER);
            }
        });

//        activityLoginBinding.loginWithLine.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                HiLoginManager.getInstance().login(LoginType.LINE);
//            }
//        });
//
//        activityLoginBinding.loginAsVisitor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                HiLoginManager.getInstance().login(LoginType.VISITOR);
//            }
//        });
    }

    @Override
    public void onInitFailed(int code, String msg) {

    }

    @Override
    public void onInitSuccess() {

    }

    @Override
    public void onLogout() {

    }

    @Override
    public void onLoginSuccess(HiUser user) {
            finish();
    }

    @Override
    public void onLoginFailed(int code, String msg) {

    }

    @Override
    public void onUpgradeSuccess(HiUser user) {

    }

    @Override
    public void onProductsResult(int code, List<HiProduct> products) {

    }

    @Override
    public void onPaySuccess(HiOrder order) {

    }

    @Override
    public void onPayFailed(int code, String msg) {

    }

    @Override
    public void onExitSuccess() {

    }
}
