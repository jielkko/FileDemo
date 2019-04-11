package com.hjl.filepicker.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/6/23.
 */

public class DataUtil {
    private static final String TAG = "DataStringUtil";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    // 获取当天时间
    public static String getNewName(){
        return  dateFormat.format(new Date());
    }


}
