package com.hjl.filepicker.utils;

import com.hjl.filepicker.R;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/6/20.
 */

public class FileUtil {


    //.doc,.docx,.pdf,.xls,.xlsx,.ppt,.pptx,.zip,.rar
    /* 取得扩展名 对应的图标*/
    public static int getFileIcon(String orgName) {



        if(orgName == null || orgName.equals("")){
            return R.drawable.file_other;
        }
        /* 取得扩展名 */
        String end = orgName;
        /* 依扩展名的类型决定MimeType */
        if (end.indexOf("jpg") != -1 || end.indexOf("gif") != -1 || end.indexOf("png") != -1 || end.indexOf("jpeg") != -1) {
            return R.drawable.file_pic;
        } else if (end.indexOf("doc") != -1 || end.indexOf("docx") != -1) {
            return R.drawable.file_word;
        } else if (end.indexOf("ppt") != -1 || end.indexOf("pptx") != -1) {
            return R.drawable.file_ppt;
        } else if (end.indexOf("xls") != -1 || end.indexOf("xlsx") != -1) {
            return R.drawable.file_xls;
        } else if (end.indexOf("pdf") != -1) {
            return R.drawable.file_pdf;
        } else if (end.indexOf("zip") != -1 || end.indexOf("rar") != -1) {
            return R.drawable.file_zip;
        } else if (end.indexOf("txt") != -1) {
            return R.drawable.file_txt;
        } else {
            return R.drawable.file_other;
        }
    }


    public static String fileSizeConver(long fileS) {
        //DecimalFormat df = new DecimalFormat("#.00"); //两位小数
        DecimalFormat df = new DecimalFormat("#.0");//一位小数
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

}
