package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherAlarm {

    @JsonProperty("Date")
    private String date;

    @JsonProperty("AlarmType")
    private String alarmType;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }
}