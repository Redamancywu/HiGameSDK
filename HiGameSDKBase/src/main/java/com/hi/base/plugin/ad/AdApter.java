package com.hi.base.plugin.ad;

public abstract class AdApter implements IAd{
    protected IAdInitializationListener initializationListener;

    @Override
    public void setInitializationListener(IAdInitializationListener initializationListener) {
        this.initializationListener = initializationListener;
    }
}
