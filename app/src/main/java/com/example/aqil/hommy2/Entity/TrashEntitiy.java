package com.example.aqil.hommy2.Entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class TrashEntitiy extends ArrayList<Parcelable> implements Parcelable {

    String homeTitle;

    public String getHomeTitle() {
        return homeTitle;
    }

    public String imagePath;
    public String thumbPath;
    public String namePath;

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String videoPath;
    int categoryno;


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public int getCategoryno() {
        return categoryno;
    }

    public void setCategoryno(int categoryno) {
        this.categoryno = categoryno;
    }

    public static Creator<TrashEntitiy> getCREATOR() {
        return CREATOR;
    }

    public TrashEntitiy(String HomeTitle, String imagePath, String thumbPath, String namePath, int categoryno, String videoPath) {
        this.homeTitle = HomeTitle;
        this.imagePath = imagePath;
        this.thumbPath = thumbPath;
        this.namePath = namePath;
        this.categoryno = categoryno;
        this.videoPath = videoPath;
    }

    public TrashEntitiy() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.homeTitle);
        dest.writeString(this.imagePath);
        dest.writeString(this.thumbPath);
        dest.writeString(this.namePath);
        dest.writeString(this.videoPath);
        dest.writeInt(this.categoryno);
    }

    protected TrashEntitiy(Parcel in) {
        this.homeTitle = in.readString();
        this.imagePath = in.readString();
        this.thumbPath = in.readString();
        this.namePath = in.readString();
        this.videoPath = in.readString();
        this.categoryno = in.readInt();
    }

    public static final Creator<TrashEntitiy> CREATOR = new Creator<TrashEntitiy>() {
        @Override
        public TrashEntitiy createFromParcel(Parcel source) {
            return new TrashEntitiy(source);
        }

        @Override
        public TrashEntitiy[] newArray(int size) {
            return new TrashEntitiy[size];
        }
    };
}
