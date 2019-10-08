package com.hjl.filedemo.common;


import android.app.Application;

import com.hjl.filepicker.FilePicker;

public class MyApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    private Boolean isDebug = true;

    @Override
    public void onCreate() {
        super.onCreate();



        //一般在Application初始化配置一次就可以
        FilePicker.getInstance().selectLimit = 9;
        FilePicker.getInstance().getFolderData();
    }


}
