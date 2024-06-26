package com.hi.base.plugin.ad;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class AdContainer {
    public static int POS_TOP = 1;
    public static int POS_BOTTOM = 2;

    /**
     * 生成banner广告的容器，在页面顶部或底部
     * @param activity
     * @param pos banner所在位置，1：顶部；2：底部
     * @return
     */
    public static ViewGroup generateBannerViewContainer(Activity activity, int pos) {
        View decorView = activity.getWindow().getDecorView();
        FrameLayout contentParent =
                (FrameLayout) decorView.findViewById(android.R.id.content);

        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.FILL_HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        contentParent.addView(layout, layoutParams);

        // 设置layout_gravity
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) layout.getLayoutParams();
        if (pos == POS_TOP) {
            params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        } else {
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        }
        layout.setLayoutParams(params);

        layout.requestLayout();
        return layout;
    }

    public static void destroySelf(ViewGroup container) {
        if (container != null) {
            ViewGroup parent = (ViewGroup) container.getParent();
            if (parent != null) {
                parent.removeView(container);
            }
            container.removeAllViews();
        }
    }
}
