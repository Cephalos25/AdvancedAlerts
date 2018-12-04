package com.example.advancedalarm;

public class Alert {
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
