package com.hjl.filepicker;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.hjl.filepicker.bean.FileItem;
import com.hjl.filepicker.utils.DataUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FilePicker {

    public static final String TAG = FilePicker.class.getSimpleName();

    public static final int RESULT_CODE_FILES = 5654;
    public static final String EXTRA_RESULT_FILES = "extra_result_files";

    public ArrayList<FileItem> mSelectedFiles = new ArrayList<>(); //所有的图片文件夹
    public ArrayList<FileItem> mAllFiles = new ArrayList<>();    //选中的图片集合

    public ArrayList<FileItem> docList = new ArrayList<>();    //集合
    public ArrayList<FileItem> pptList = new ArrayList<>();    //集合
    public ArrayList<FileItem> xlsList = new ArrayList<>();    //集合
    public ArrayList<FileItem> pdfList = new ArrayList<>();    //集合


    public int selectLimit = 9;    //最多选择
    private static FilePicker mInstance;

    private FilePicker() {
    }

    public static FilePicker getInstance() {
        if (mInstance == null) {
            synchronized (FilePicker.class) {
                if (mInstance == null) {
                    mInstance = new FilePicker();
                }
            }
        }
        return mInstance;
    }


    //                结果：
//                doc :: application/msword
//                docx :: application/vnd.openxmlformats-officedocument.wordprocessingml.document
//                ppt :: application/vnd.ms-powerpoint
//                pptx :: application/vnd.openxmlformats-officedocument.presentationml.presentation
//                xls :: application/vnd.ms-excel
//                xlsx :: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
//                pdf :: application/pdf
//                txt :: text/plain
//                rar :: application/rar
//                zip :: application/zip


    /**
     * Event类型
     */

    public enum MIME_TYPE {
        doc,         //
        ppt,         //
        xls,         //
        pdf,         //
        txt,         //
        rar,         //
        zip,         //
    }

    //doc,ppt,xls,pdf
    public static String[] getArgs(String text) {


        List<String> list = new ArrayList<>();
        if (text.indexOf("doc") != -1 || text.indexOf("docx") != -1) {
            list.add("application/msword");
            list.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        }

        if (text.indexOf("ppt") != -1 || text.indexOf("pptx") != -1) {
            list.add("application/vnd.ms-powerpoint");
            list.add("application/vnd.openxmlformats-officedocument.presentationml.presentation");
        }
        if (text.indexOf("xls") != -1 || text.indexOf("xlsx") != -1) {
            list.add("application/vnd.ms-excel");
            list.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        }
        if (text.indexOf("pdf") != -1) {
            list.add("application/pdf");
        }
        String[] selectionArgs = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            selectionArgs[i] = list.get(i);
        }

        return selectionArgs;

    }


    public static ArrayList<FileItem> getList(String text) {
        if (text.equals("")) {
            return FilePicker.getInstance().mAllFiles;
        }
        if (text.equals("doc")) {
            return FilePicker.getInstance().docList;
        }
        if (text.equals("ppt")) {
            return FilePicker.getInstance().pptList;
        }
        if (text.equals("xls")) {
            return FilePicker.getInstance().xlsList;
        }
        if (text.equals("pdf")) {
            return FilePicker.getInstance().pdfList;
        }
        return FilePicker.getInstance().mAllFiles;
    }

    public static void setSelected(String text, int layoutPosition, int isSelected) {

        if (text.equals("")) {
            FilePicker.getInstance().mAllFiles.get(layoutPosition).isSelected = isSelected;
        }
        if (text.equals("doc")) {
            FilePicker.getInstance().docList.get(layoutPosition).isSelected = isSelected;
        }
        if (text.equals("ppt")) {
            FilePicker.getInstance().pptList.get(layoutPosition).isSelected = isSelected;
        }
        if (text.equals("xls")) {
            FilePicker.getInstance().xlsList.get(layoutPosition).isSelected = isSelected;
        }
        if (text.equals("pdf")) {
            FilePicker.getInstance().pdfList.get(layoutPosition).isSelected = isSelected;
        }
    }

    public static void clearList() {
        FilePicker.getInstance().mAllFiles.clear();
        FilePicker.getInstance().mSelectedFiles.clear();
        FilePicker.getInstance().docList.clear();
        FilePicker.getInstance().pptList.clear();
        FilePicker.getInstance().xlsList.clear();
        FilePicker.getInstance().pdfList.clear();

    }

    ProgressDialog  progressDialog;
    public void goSelectFile(final Activity mActivity, final int RESULT_CODE){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(mActivity, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("正在加载中...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();

        if(!FilePicker.getInstance().isLoadingFolder){
            progressDialog.dismiss();
            Intent intent = new Intent(mActivity, FileGridActivity.class);
            mActivity.startActivityForResult(intent, RESULT_CODE);

        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    goSelectFile(mActivity,RESULT_CODE);
                }
            }, 1000);//3秒后执行Runnable中的run方法

        }

    }


    public Boolean isLoadingFolder = false;
    public Boolean isUpdateDate = false;
    /**
     * 遍历文件夹中资源
     */
    public void getFolderData() {
        isLoadingFolder = true;
        new Thread() {
            @Override
            public void run() {
                super.run();
                scanDirNoRecursion(Environment.getExternalStorageDirectory().toString());
            }
        }.start();

    }
    /**
     * 非递归
     *
     * @param path
     */
    public void scanDirNoRecursion(String path) {
        FilePicker.getInstance().clearList();

/*        ArrayList<FileItem> mAllFiles1 = new ArrayList<>();    //选中的图片集合
        ArrayList<FileItem> docList1 = new ArrayList<>();    //集合
        ArrayList<FileItem> pptList1 = new ArrayList<>();    //集合
        ArrayList<FileItem> xlsList1 = new ArrayList<>();    //集合
        ArrayList<FileItem> pdfList1 = new ArrayList<>();    //集合*/


        LinkedList list = new LinkedList();
        File dir = new File(path);
        File file[] = dir.listFiles();
        if (file == null) return;
        for (int i = 0; i < file.length; i++) {
            if (file[i].isDirectory())
                list.add(file[i]);
            else {
                System.out.println(file[i].getAbsolutePath());
            }
        }
        File tmp;
        while (!list.isEmpty()) {
            tmp = (File) list.removeFirst();//首个目录
            if (tmp.isDirectory()) {
                file = tmp.listFiles();
                if (file == null)
                    continue;
                for (int i = 0; i < file.length; i++) {
                    if (file[i].isDirectory())
                        list.add(file[i]);//目录则加入目录列表，关键
                    else {
//                        System.out.println(file[i]);

                        //封装实体
                        FileItem fileItem = new FileItem();
                        fileItem.name = file[i].getName();
                        fileItem.path = file[i].getPath();
                        fileItem.size = file[i].length();
                        int lastDotIndex = file[i].getName().lastIndexOf(".");
                        if (lastDotIndex > 0) {
                            fileItem.mimeType = file[i].getName().substring(lastDotIndex + 1);
                        }
                        fileItem.addTime = file[i].lastModified();


                        String imageName = fileItem.name;


                       FilePicker.getInstance().mAllFiles.add(fileItem);
                      if (imageName.indexOf("doc") != -1 || imageName.indexOf("docx") != -1) {
                            FilePicker.getInstance().docList.add(fileItem);
                        }
                        if (imageName.indexOf("ppt") != -1 || imageName.indexOf("pptx") != -1) {
                            FilePicker.getInstance().pptList.add(fileItem);
                        }
                        if (imageName.indexOf("xls") != -1 || imageName.indexOf("xlsx") != -1) {
                            FilePicker.getInstance().xlsList.add(fileItem);
                        }
                        if (imageName.indexOf("pdf") != -1) {
                            FilePicker.getInstance().pdfList.add(fileItem);
                        }

                      /*  if (imageName.indexOf("doc") != -1 || imageName.indexOf("docx") != -1) {
                            docList1.add(fileItem);
                        }
                        if (imageName.indexOf("ppt") != -1 || imageName.indexOf("pptx") != -1) {
                            pptList1.add(fileItem);
                        }
                        if (imageName.indexOf("xls") != -1 || imageName.indexOf("xlsx") != -1) {
                            xlsList1.add(fileItem);
                        }
                        if (imageName.indexOf("pdf") != -1) {
                            pdfList1.add(fileItem);
                        }*/
                    }
                }

                isLoadingFolder = false;
          /*      FilePicker.getInstance().mAllFiles.addAll(mAllFiles1);
                FilePicker.getInstance().docList.addAll(docList1);
                FilePicker.getInstance().pptList.addAll(pptList1);
                FilePicker.getInstance().xlsList.addAll(xlsList1);
                FilePicker.getInstance().pdfList.addAll(pdfList1);*/

            } else {
                System.out.println(tmp);
            }
        }
    }
}