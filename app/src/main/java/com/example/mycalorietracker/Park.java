package com.example.mycalorietracker;

public class Park {
    private double latitude;
    private double longitude;
    private String namePark;

    public Park(double latitude, double longitude, String namePark) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.namePark = namePark;
    }

    public Park() {

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getNamePark() {
        return namePark;
    }

    public void setNamePark(String namePark) {
        this.namePark = namePark;
    }
}
