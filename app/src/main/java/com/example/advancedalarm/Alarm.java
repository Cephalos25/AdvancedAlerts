package com.example.advancedalarm;

import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;

public class Alarm extends Object{
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
