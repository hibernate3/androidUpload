package com.example.xutils3download;

import android.app.Application;

import org.xutils.x;

/**
 * Created by wangyuhang on 2017/2/8.
 */

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
    }
}
