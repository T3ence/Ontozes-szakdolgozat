package com.example.ontozes.data;

public class VizData {
    private double homerseklet;
    private double nyomas;
    private boolean aramlas;
    private boolean vegyszeradagolas;

    public VizData() {
        homerseklet = 0;
        nyomas = 0;
        aramlas = false;
        vegyszeradagolas = false;
    }


    public double getNyomas() {
        return nyomas;
    }

    public void setNyomas(double nyomas) {
        this.nyomas = nyomas;
    }

    public boolean isAramlas() {
        return aramlas;
    }

    public void setAramlas(boolean aramlas) {
        this.aramlas = aramlas;
    }

    public boolean isVegyszeradagolas() {
        return vegyszeradagolas;
    }

    public void setVegyszeradagolas(boolean vegyszeradagolas) {
        this.vegyszeradagolas = vegyszeradagolas;
    }

    public double getHomerseklet() {
        return homerseklet;
    }

    public void setHomerseklet(double homerseklet) {
        this.homerseklet = homerseklet;
    }
}
