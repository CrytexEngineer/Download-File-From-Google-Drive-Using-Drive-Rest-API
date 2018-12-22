package com.example.aqil.hommy2.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class UserAccount implements Parcelable {
    String name;
    String photoUrl;
    String mail;


    public UserAccount(String name, String photoUrl, String mail) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.photoUrl);
        dest.writeString(this.mail);
    }

    protected UserAccount(Parcel in) {
        this.name = in.readString();
        this.photoUrl = in.readString();
        this.mail = in.readString();
    }

    public static final Creator<UserAccount> CREATOR = new Creator<UserAccount>() {
        @Override
        public UserAccount createFromParcel(Parcel source) {
            return new UserAccount(source);
        }

        @Override
        public UserAccount[] newArray(int size) {
            return new UserAccount[size];
        }
    };


}