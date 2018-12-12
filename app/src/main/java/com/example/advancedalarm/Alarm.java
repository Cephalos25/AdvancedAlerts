package com.example.advancedalarm;

import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;

public class Alarm implements Parcelable{
    private String name;
    private LocalDateTime eventDate;
    private Alert alert;

    public static ArrayList<Alarm> alarmList = new ArrayList<>();

    public Alarm(String name, LocalDateTime eventDate, Alert alert) {
        this.name = name;
        this.eventDate = eventDate;
        this.alert = alert;
    }

    public Alarm() {
        this.name = "blank";
        this.eventDate = null;
        this.alert = new Alert();
    }

    protected Alarm(Parcel in) {
        this.name = in.readString();
        this.alert = (Alert)in.readParcelable(Alarm.class.getClassLoader());
        this.eventDate = LocalDateTime.parse(in.readString());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(alert, flags);
        dest.writeString(eventDate.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }
}
