package com.hi.base.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;

import com.hi.base.ui.utils.HiActivityHelper;
import com.hi.base.utils.ResourceUtils;


/**
 * 基础FragmentActivity组件，所有SDK相关的Activity都继承该类
 * 这里封装了Fragment的添加方法
 */
public class BaseFragmentActivity extends FragmentActivity implements FragmentOnAttachListener {

    /**
     * Fragment 容器资源ID
     */
    protected int containerResId;

    /**
     * Fragment容器
     */
    protected FrameLayout fragmentContainer;

    /**
     * Fragment管理器
     */
    protected FragmentManager fragmentManager;

    /**
     * 当前Activity监听器分发器
     */
    protected ActivityListeners listeners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(ResourceUtils.getResourceID(this, "R.layout.ug_layout_base_fragment_container"));

//        if (GlobalConfig.getInstance().isOrientationLandscape()) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }

        WindowManager.LayoutParams windowAttributes = getWindow().getAttributes();
        windowAttributes.rotationAnimation = WindowManager.LayoutParams.ROTATION_ANIMATION_SEAMLESS;
        getWindow().setAttributes(windowAttributes);

        getWindow().setLayout(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);

        containerResId = ResourceUtils.getResourceID(this, "R.id.ug_base_container");
        fragmentContainer = this.findViewById(containerResId);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addFragmentOnAttachListener(this);

        // 注入透明的Fragment，管理生命周期和onActivityResult等事件
        BaseDispatchFragment.injectToActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      //  Log.d(Constants.TAG, "onActivityResult called in activity. requestCode:" + requestCode);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    public void onAttachFragment(FragmentManager fragmentManager, Fragment fragment) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fragmentManager != null) {
            fragmentManager.removeFragmentOnAttachListener(this);
        }
    }

    // 添加一个fragement
    protected void push(Fragment fragment) {

        //UGActivityHelper.push(this, fragment);
    }

    // 查找或者创建一个fragment
    protected   <T extends Fragment> T findOrCreateFragment(Class<T> fragmentClass) {

        return HiActivityHelper.findOrCreateFragment(fragmentClass, this, fragmentClass.getCanonicalName());
    }

    /**
     * 获取容器id
     * @return
     */
    public int getContainerResId() {

        return containerResId;
    }

    /**
     * 获取Activity 监听器管理类
     * @return
     */
    public ActivityListeners getListeners() {
        if(listeners == null) {
            listeners = new ActivityListeners();
        }
        return listeners;
    }

    /**
     * 获取 BaseDispatchFragment
     * @return
     */
    public Fragment getDispatchFragment() {
        return this.getSupportFragmentManager().findFragmentByTag(BaseDispatchFragment.class.getCanonicalName());
    }

//    public void showLoading() {
//        UGLoadingUtils.showLoading(this);
//    }
//
//    public void hideLoading() {
//        UGLoadingUtils.hideLoading();
//    }


}
