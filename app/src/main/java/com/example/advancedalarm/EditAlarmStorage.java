package com.example.advancedalarm;

import android.os.Parcel;
import android.os.Parcelable;

class EditAlarmStorage implements Parcelable{
    private int month;
    private int day;
    private int year;
    private int hour;
    private int minute;
    private boolean importance;
    private String shortdescription;

    public EditAlarmStorage(int month, int day, int year, int hour, int minute, boolean importance, String shortdescription) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.importance = importance;
        this.shortdescription = shortdescription;
    }

    protected EditAlarmStorage(Parcel in) {
        month = in.readInt();
        day = in.readInt();
        year = in.readInt();
        hour = in.readInt();
        minute = in.readInt();
        importance = in.readByte() != 0;
        shortdescription = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(month);
        dest.writeInt(day);
        dest.writeInt(year);
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeByte((byte) (importance ? 1 : 0));
        dest.writeString(shortdescription);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EditAlarmStorage> CREATOR = new Creator<EditAlarmStorage>() {
        @Override
        public EditAlarmStorage createFromParcel(Parcel in) {
            return new EditAlarmStorage(in);
        }

        @Override
        public EditAlarmStorage[] newArray(int size) {
            return new EditAlarmStorage[size];
        }
    };
}
