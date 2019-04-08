package com.hjl.filepicker;


import com.hjl.filepicker.bean.FileItem;

import java.util.ArrayList;
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


}