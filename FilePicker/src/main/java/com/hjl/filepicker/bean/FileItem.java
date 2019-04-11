package com.hjl.filepicker.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class FileItem implements Parcelable{

    public String name;       //图片的名字
    public String path;       //图片的路径
    public long size;         //图片的大小
    public String mimeType;   //图片的类型
    public long addTime;      //图片的创建时间
    public int isSelected = 0;      //图片的是否选中 0 未选择  1 选中


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeLong(this.size);
        dest.writeString(this.mimeType);
        dest.writeLong(this.addTime);
        dest.writeInt(this.isSelected);
    }

    public FileItem() {
    }

    protected FileItem(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.size = in.readLong();
        this.mimeType = in.readString();
        this.addTime = in.readLong();
        this.isSelected = in.readInt();
    }

    public static final Parcelable.Creator<FileItem> CREATOR = new Parcelable.Creator<FileItem>() {
        @Override
        public FileItem createFromParcel(Parcel source) {
            return new FileItem(source);
        }

        @Override
        public FileItem[] newArray(int size) {
            return new FileItem[size];
        }
    };





}
