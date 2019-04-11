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

    public static String timeStampToDateString(long timeStamp){
        //long timeStamp = 1495777335060;//直接是时间戳
        //long timeStamp = System.currentTimeMillis();  //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
        String dStr = dateFormat.format(new Date(timeStamp));   // 时间戳转换成时间
        //System.out.println(dStr);//打印出你要的时间
        //结果就是: 2017-05-26 13:42:15
        return dStr;
    }

}
