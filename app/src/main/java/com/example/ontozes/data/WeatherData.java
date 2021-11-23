package com.example.ontozes.data;

public class WeatherData {
    private double homerseklet;
    private int windSpeed;
    private int windDir;


    public WeatherData() {
        this.homerseklet = 0;
        this.windSpeed = 0;
        this.windDir = 0;
    }


    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDir() {
        return windDir;
    }

    public void setWindDir(int windDir) {
        this.windDir = windDir;
    }

    public double getHomerseklet() {
        return homerseklet;
    }

    public void setHomerseklet(double homerseklet) {
        this.homerseklet = homerseklet;
    }
}
