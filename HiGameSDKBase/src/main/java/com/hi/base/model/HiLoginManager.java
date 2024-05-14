package com.hi.base.model;

import com.hi.base.plugin.itf.ILogin;

public class HiLoginManager {
    private static HiLoginManager instance;
    private ILogin login;

    public static HiLoginManager getInstance() {
        if (instance==null){
            instance=new HiLoginManager();
        }
        return instance;
    }

}
