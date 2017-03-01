package com.wumart.lib.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User: 吕勇
 * Date: 2016-05-11
 * Time: 10:13
 * Description:下载实体
 */
public class DownLoadBean implements Parcelable {
    private String url;
    private String filePath;
    private String fileName;
    private int icon;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.filePath);
        dest.writeString(this.fileName);
        dest.writeInt(this.icon);
    }

    public DownLoadBean() {
    }

    protected DownLoadBean(Parcel in) {
        this.url = in.readString();
        this.filePath = in.readString();
        this.fileName = in.readString();
        this.icon = in.readInt();
    }

    public static final Parcelable.Creator<DownLoadBean> CREATOR = new Parcelable.Creator<DownLoadBean>() {
        @Override
        public DownLoadBean createFromParcel(Parcel source) {
            return new DownLoadBean(source);
        }

        @Override
        public DownLoadBean[] newArray(int size) {
            return new DownLoadBean[size];
        }
    };
}
