package com.hi.base.ui.login;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hi.base.R;
import com.hi.base.manger.HiLoginManager;
import com.hi.base.utils.Constants;
import com.hi.higamesdk_login.databinding.ActivityLoginBinding;

public class LoginActivity extends Activity {
   private static ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setScreenOrientation(); // 动态设置屏幕方向
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
    public void onLoginClick(View view) {
        switch (view.getId()){

        }
    }
}