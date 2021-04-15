package com.example.earthquake;

public class Earthquake {

    private double magnitude;
    private  String location;
    private long date;
    private String url;

    public Earthquake(double magnitude, String location, long date,String url) {
        this.magnitude = magnitude;
        this.location = location;
        this.date = date;
        this.url = url;
    }


    public double getMagnitude() {
        return magnitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Earthquake{" +
                "magnitude='" + magnitude + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                '}';
    }



}
