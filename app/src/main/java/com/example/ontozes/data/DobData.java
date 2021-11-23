package com.example.ontozes.data;

public class DobData {
    private boolean kapcsolat;
    private String uzemmod;
    private double helyzet;
    private int impulzusok;
    private double nyomas;
    private double sebesseg;


    public DobData() {
        kapcsolat = false;
        uzemmod = "";
        helyzet = 0;
        impulzusok = 0;
        nyomas = 0;
        sebesseg = 0;
    }


    public boolean isKapcsolat() {
        return kapcsolat;
    }

    public void setKapcsolat(boolean kapcsolat) {
        this.kapcsolat = kapcsolat;
    }

    public String getUzemmod() {
        return uzemmod;
    }

    public void setUzemmod(String uzemmod) {
        this.uzemmod = uzemmod;
    }

    public double getHelyzet() {
        return helyzet;
    }

    public void setHelyzet(double helyzet) {
        this.helyzet = helyzet;
    }

    public int getImpulzusok() {
        return impulzusok;
    }

    public void setImpulzusok(int impulzusok) {
        this.impulzusok = impulzusok;
    }

    public double getNyomas() {
        return nyomas;
    }

    public void setNyomas(double nyomas) {
        this.nyomas = nyomas;
    }

    public double getSebesseg() {
        return sebesseg;
    }

    public void setSebesseg(double sebesseg) {
        this.sebesseg = sebesseg;
    }
}
