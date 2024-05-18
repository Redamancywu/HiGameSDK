package com.hi.base.ui.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.hi.base.ui.base.BaseDispatchFragment;
import com.hi.base.ui.base.BaseFragmentActivity;
import com.hi.base.utils.Constants;
import com.hi.base.utils.ResourceUtils;

import java.util.List;

public class HiActivityHelper {


    /**
     * 启动其他Activity
     * @param activity
     * @param intent
     * @param requestCode
     */
    public static void startForActivity(BaseFragmentActivity activity, Intent intent, int requestCode) {

        BaseDispatchFragment.startActivityForResult(activity, intent, requestCode);

    }


    /**
     * 添加一个fragment到activity中
     * @param activity
     * @param fragment
     * @param tag
     */
    public static synchronized void push(BaseFragmentActivity activity, Fragment fragment, String tag) {

        List<Fragment> currentFragments = activity.getSupportFragmentManager().getFragments();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        if (currentFragments.size() != 0) {
            transaction.setCustomAnimations(
                    ResourceUtils.getResourceID(activity, "R.anim.ug_push_in_left"),
                    ResourceUtils.getResourceID(activity, "R.anim.ug_push_out_left"),
                    ResourceUtils.getResourceID(activity, "R.anim.ug_push_in_right"),
                    ResourceUtils.getResourceID(activity, "R.anim.ug_push_out_right")
            );
        }

        if (fragment.isAdded() || activity.getSupportFragmentManager().findFragmentByTag(tag) != null) {
            // 已经添加过了
            Log.d(Constants.TAG, "fragment exists. just show...");
            transaction.show(fragment);
        } else {
            transaction.add(activity.getContainerResId(), fragment, tag);
            Log.d(Constants.TAG, "UGActivityHelper::add the fragment into stack:" + currentFragments.size() + "; tag:"+tag);
            if (currentFragments.size() != 0) {
                Fragment lastFragment = currentFragments.get(currentFragments.size() - 1);
                if (lastFragment != null) {
                    transaction.hide(lastFragment);
                } else {
                    Log.w(Constants.TAG, "UGActivityHelper::push notice. last fragment is null.");
                }
                transaction.addToBackStack(tag);
            }
        }

        transaction.commitAllowingStateLoss();
    }



    public static void push(BaseFragmentActivity activity, Fragment fragment) {
        push(activity, fragment, fragment.getClass().getCanonicalName());
    }

    public static void switchFragment(BaseFragmentActivity activity, Fragment from, Fragment to) {

        Log.d(Constants.TAG, "switchFragment called.");
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //检测去往的Fragment是否被添加
        if (!to.isAdded()) {
            //如果没有添加，就隐藏当前Fragment，添加下一个Fragment
            ft.hide(from).add(activity.getContainerResId(), to, to.getClass().getCanonicalName()).commitAllowingStateLoss();
        } else {
            //如果已经被添加，就隐藏当前Fragment，直接显示下一个Fragement
            ft.hide(from).show(to).commitAllowingStateLoss();
        }
    }


    /**
     * 查找或者创建一个fragment
     * @param fragmentClass
     * @param activity
     * @param tag
     * @param <T>
     * @return
     */
    public  static <T extends Fragment> T findOrCreateFragment(Class<T> fragmentClass, BaseFragmentActivity activity, String tag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        T fragment = (T) fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }

    /**
     * 检查EditText是否为空
     * @param context
     * @param txt
     * @param tipRes
     * @return
     */
    public static boolean checkEmpty(Activity context, EditText txt, String tipRes) {
        if(txt == null) return true;

        String val = txt.getText().toString();
        if(TextUtils.isEmpty(val)) {
            Toast.makeText(context, ResourceUtils.getString(context, tipRes), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    /**
     * Fragment回退
     * @param activity
     * @param fragmentManager
     * @return
     */
    public static void backStacks(Activity activity, FragmentManager fragmentManager) {
        if(fragmentManager.getBackStackEntryCount() <= 0)//这里是取出我们返回栈存在Fragment的个数
            activity.finish();
        else {
            fragmentManager.popBackStackImmediate();
            if (fragmentManager.getBackStackEntryCount() <= 0) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity.finish();
                    }
                }, 300);

            }
        }
    }

}
