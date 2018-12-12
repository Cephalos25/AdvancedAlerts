package com.example.advancedalarm;

import android.os.Parcel;
import android.os.Parcelable;

public class Alert implements Parcelable{
    private String shortDescription;
    private String description;
    private boolean important;

    public Alert(String shortDescription, String description, boolean important) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.important = important;
    }

    public Alert(String description, boolean important) {
        this.shortDescription = null;
        this.description = description;
        this.important = important;
    }

    public Alert() {
        this.shortDescription = null;
        this.description = null;
        this.important = false;
    }

    protected Alert(Parcel in) {
        shortDescription = in.readString();
        description = in.readString();
        important = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeByte((byte) (important ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Alert> CREATOR = new Creator<Alert>() {
        @Override
        public Alert createFromParcel(Parcel in) {
            return new Alert(in);
        }

        @Override
        public Alert[] newArray(int size) {
            return new Alert[size];
        }
    };

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }
}
