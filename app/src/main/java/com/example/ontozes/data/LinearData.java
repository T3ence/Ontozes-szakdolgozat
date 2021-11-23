package com.example.ontozes.data;

public class LinearData {

    private boolean kapcsolat;
    private String uzemmod;
    private boolean irany; //true = elore, false = hatra
    private double ontozesi_mennyiseg;
    private double sebesseg;
    private double teljes_tavolsag;
    private double aktualis_tavolsag_kezdettol;
    private double viz_nyomas;
    private boolean vilagitas;
    private boolean elso_kocsi;
    private boolean utolso_kocsi;

    private double lon;
    private double lat;
    private double kezdet_lon;
    private double kezdet_lat;
    private double vege_lon;
    private double vege_lat;

    public LinearData() {
        this.kapcsolat = false;
        this.uzemmod = "";
        this.irany = false;
        this.ontozesi_mennyiseg = 0;
        this.sebesseg = 0;
        this.teljes_tavolsag = 0;
        this.aktualis_tavolsag_kezdettol = 0;
        this.viz_nyomas = 0;
        this.vilagitas = false;
        this.elso_kocsi = false;
        this.utolso_kocsi = false;

        this.lon = 0;
        this.lat = 0;
        this.kezdet_lon = 0;
        this.kezdet_lat = 0;
        this.vege_lon = 0;
        this.vege_lat = 0;
    }


    public double getOntozesi_mennyiseg() {
        return ontozesi_mennyiseg;
    }

    public void setOntozesi_mennyiseg(double ontozesi_mennyiseg) {
        this.ontozesi_mennyiseg = ontozesi_mennyiseg;
    }


    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getKezdet_lon() {
        return kezdet_lon;
    }

    public void setKezdet_lon(double kezdet_lon) {
        this.kezdet_lon = kezdet_lon;
    }

    public double getKezdet_lat() {
        return kezdet_lat;
    }

    public void setKezdet_lat(double kezdet_lat) {
        this.kezdet_lat = kezdet_lat;
    }

    public double getVege_lon() {
        return vege_lon;
    }

    public void setVege_lon(double vege_lon) {
        this.vege_lon = vege_lon;
    }

    public double getVege_lat() {
        return vege_lat;
    }

    public void setVege_lat(double vege_lat) {
        this.vege_lat = vege_lat;
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

    public boolean isIrany() {
        return irany;
    }

    public void setIrany(boolean irany) {
        this.irany = irany;
    }

    public double getSebesseg() {
        return sebesseg;
    }

    public void setSebesseg(double sebesseg) {
        this.sebesseg = sebesseg;
    }

    public double getTeljes_tavolsag() {
        return teljes_tavolsag;
    }

    public void setTeljes_tavolsag(double teljes_tavolsag) {
        this.teljes_tavolsag = teljes_tavolsag;
    }

    public double getAktualis_tavolsag_kezdettol() {
        return aktualis_tavolsag_kezdettol;
    }

    public void setAktualis_tavolsag_kezdettol(double aktualis_tavolsag_kezdettol) {
        this.aktualis_tavolsag_kezdettol = aktualis_tavolsag_kezdettol;
    }

    public double getViz_nyomas() {
        return viz_nyomas;
    }

    public void setViz_nyomas(double viz_nyomas) {
        this.viz_nyomas = viz_nyomas;
    }

    public boolean isVilagitas() {
        return vilagitas;
    }

    public void setVilagitas(boolean vilagitas) {
        this.vilagitas = vilagitas;
    }

    public boolean isElso_kocsi() {
        return elso_kocsi;
    }

    public void setElso_kocsi(boolean elso_kocsi) {
        this.elso_kocsi = elso_kocsi;
    }

    public boolean isUtolso_kocsi() {
        return utolso_kocsi;
    }

    public void setUtolso_kocsi(boolean utolso_kocsi) {
        this.utolso_kocsi = utolso_kocsi;
    }

}
