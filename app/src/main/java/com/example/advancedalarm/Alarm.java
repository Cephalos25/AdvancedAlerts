package com.example.advancedalarm;

import java.util.Date;

public class Alarm {
    private String name;
    private Date eventDate;
    private Alert alert;

    public Alarm(String name, Date eventDate, Alert alert) {
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

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }
}
