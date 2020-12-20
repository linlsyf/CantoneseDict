package com.ui.other.bean;


import com.easy.recycleview.bean.DyItemBean;

public class WeatherMsg  extends DyItemBean {
    private String date;
    private String week;
    private String temperature;
    private String wind;
    private String dayTime;
    private String night;

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }
}
