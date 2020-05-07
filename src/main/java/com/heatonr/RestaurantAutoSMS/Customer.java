package com.heatonr.RestaurantAutoSMS;

import javafx.scene.control.CheckBox;

public class Customer {
    private String phoneNumber;
    private String name;
    private long startTime;
    private CheckBox completed;

    public CheckBox getCompleted() {
        return completed;
    }

    public void setCompleted(CheckBox completed) {
        this.completed = completed;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public long getStartTime() {
        return startTime;
    }

    public Customer(String pPhoneNumber, String pName, long pstartTime){
        phoneNumber = pPhoneNumber;
        name = pName;
        startTime = pstartTime;
        completed = new CheckBox();
    }
}
