package com.hi.base.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.hi.base.utils.Constants;


/**
 * 我们在BaseFragementActivity中，增加一个透明的Fagment来处理Activity的生命周期回调以及onActivityResult等事件
 */
public class BaseDispatchFragment extends Fragment {

    private ActivityListeners listeners;
    private Activity currActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.currActivity = (Activity) context;
    }

    /**
     * 注入当前透明的Fragment，用于管理当前Activity的生命周期函数和onActivityResult回调等
     * @param activity
     */
    public static void injectToActivity(BaseFragmentActivity activity) {
        Log.d(Constants.TAG, "injectToActivity called for " + activity.getLocalClassName());
        BaseDispatchFragment fragment = activity.findOrCreateFragment(BaseDispatchFragment.class);
        if (!fragment.isAdded() && null == activity.getSupportFragmentManager().findFragmentByTag(fragment.getClass().getCanonicalName())) {
            try {
                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.add(activity.getContainerResId(), fragment, fragment.getClass().getCanonicalName());
                transaction.commitNowAllowingStateLoss();
                activity.getSupportFragmentManager().executePendingTransactions();
            } catch (Exception e) {
                Log.w(Constants.TAG, "injectToActivity add fragment failed with exception.", e);
                e.printStackTrace();
            }

            Log.d(Constants.TAG, "injectToActivity add fragment end.");
        }

        fragment.listeners = activity.getListeners();
    }
    /**
     * 从Activity中需要启动其他Activity，通过这里启动，并处理结果
     * @param activity
     * @param intent
     * @param requestCode
     */
    public static void startActivityForResult(BaseFragmentActivity activity, Intent intent, int requestCode) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        BaseDispatchFragment fragment = (BaseDispatchFragment)fragmentManager.findFragmentByTag(BaseDispatchFragment.class.getCanonicalName());
        if(fragment == null) {
            Log.e(Constants.TAG, "startActivityForResult failed. BaseDispatchFragment not exists in curr activity:" + activity.getLocalClassName());
            return;
        }

        fragment.startActivityForResult(intent, requestCode);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(Constants.TAG, "onActivityResult called in BaseDispatchFragment. size:" + listeners.size());
        if(listeners != null){
            listeners.onActivityResult(currActivity, requestCode, resultCode, data);
        }
    }
    public void onPause() {
        super.onPause();
        if(listeners != null) {
            listeners.onPause(currActivity);
        }
    }
    public void onResume() {
        super.onResume();
        if(listeners != null) {
            listeners.onResume(currActivity);
        }
    }
    public void onDestroy() {
        super.onDestroy();
        if(listeners != null) {
            listeners.onDestroy(currActivity);
        }
    }
}
