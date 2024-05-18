package com.hi.base.ui.base;

import android.content.Context;

import androidx.fragment.app.Fragment;


/**
 * 所有Fragment的基类
 */
public class BaseFragment extends Fragment {

    protected BaseFragmentActivity activity;
    protected IBackCallback backCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (BaseFragmentActivity)context;
    }

    public void setBackCallback(IBackCallback callback) {
        this.backCallback = callback;
    }

    public interface IBackCallback {
        void onBack();
    }

//    protected void showLoading() {
//        HiLoadingUtils.showLoading(activity);
//    }
//
//    protected void hideLoading() {
//        HiLoadingUtils.hideLoading();
//    }
}
