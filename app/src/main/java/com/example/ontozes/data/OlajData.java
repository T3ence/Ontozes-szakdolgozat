package com.example.ontozes.data;

public class OlajData {
    private double homerseklet;
    private double nyomas;
    private int gazolajSzint;

    public OlajData() {
        homerseklet = 0;
        nyomas = 0;
        gazolajSzint = 0;
    }

    public double getHomerseklet() {
        return homerseklet;
    }

    public void setHomerseklet(double homerseklet) {
        this.homerseklet = homerseklet;
    }

    public double getNyomas() {
        return nyomas;
    }

    public void setNyomas(double nyomas) {
        this.nyomas = nyomas;
    }

    public int getGazolajSzint() {
        return gazolajSzint;
    }

    public void setGazolajSzint(int gazolajSzint) {
        this.gazolajSzint = gazolajSzint;
    }
}
