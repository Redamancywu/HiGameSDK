package com.hi.base.ui.base;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity Listeners 集中管理和分发
 */
public class ActivityListeners {

    private List<IActivityListener> listeners;

    public ActivityListeners() {
        listeners = new ArrayList<>();
    }

    public void addListener(IActivityListener lifeCycle) {
        if (!listeners.contains(lifeCycle)) {
            listeners.add(lifeCycle);
        }
    }


    public void removeListener(IActivityListener lifeCycle) {
        if (listeners.contains(lifeCycle)) {
            listeners.remove(lifeCycle);
        }
    }


    public void onActivityResult(Activity context, int requestCode, int resultCode, Intent data) {

        for (IActivityListener cycle : this.listeners) {

            cycle.onActivityResult(context, requestCode, resultCode, data);

        }

    }


    public void onPause(Activity context) {
        for (IActivityListener cycle : this.listeners) {

            cycle.onPause(context);

        }
    }


    public void onResume(Activity context) {
        for (IActivityListener cycle : this.listeners) {

            cycle.onResume(context);

        }
    }

    public void onDestroy(Activity context) {
        for (IActivityListener cycle : this.listeners) {
            cycle.onDestroy(context);
        }
    }

    public int size() {

        return this.listeners.size();
    }
}
