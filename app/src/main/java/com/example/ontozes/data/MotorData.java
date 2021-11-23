package com.example.ontozes.data;

public class MotorData {
    private int kivantPoz;
    private double akkuFeszultseg;
    private int aktualisFordulat;
    private String kuplungPozicio;
    private String inditastol_eltelt_ido;

    private String futasidok_datum_elso;
    private String futasidok_ido_elso;
    private String futasidok_eredet_elso;

    private String futasidok_datum_masodik;
    private String futasidok_ido_masodik;
    private String futasidok_eredet_masodik;

    private String futasidok_datum_harmadik;
    private String futasidok_ido_harmadik;
    private String futasidok_eredet_harmadik;

    public String getFutasidok_eredet_elso() {
        return futasidok_eredet_elso;
    }

    public void setFutasidok_eredet_elso(String futasidok_eredet_elso) {
        this.futasidok_eredet_elso = futasidok_eredet_elso;
    }

    public String getFutasidok_eredet_masodik() {
        return futasidok_eredet_masodik;
    }

    public void setFutasidok_eredet_masodik(String futasidok_eredet_masodik) {
        this.futasidok_eredet_masodik = futasidok_eredet_masodik;
    }

    public String getFutasidok_eredet_harmadik() {
        return futasidok_eredet_harmadik;
    }

    public void setFutasidok_eredet_harmadik(String futasidok_eredet_harmadik) {
        this.futasidok_eredet_harmadik = futasidok_eredet_harmadik;
    }

    public String getFutasidok_datum_elso() {
        return futasidok_datum_elso;
    }

    public void setFutasidok_datum_elso(String futasidok_datum_elso) {
        this.futasidok_datum_elso = futasidok_datum_elso;
    }

    public String getFutasidok_ido_elso() {
        return futasidok_ido_elso;
    }

    public void setFutasidok_ido_elso(String futasidok_ido_elso) {
        this.futasidok_ido_elso = futasidok_ido_elso;
    }

    public String getFutasidok_datum_masodik() {
        return futasidok_datum_masodik;
    }

    public void setFutasidok_datum_masodik(String futasidok_datum_masodik) {
        this.futasidok_datum_masodik = futasidok_datum_masodik;
    }

    public String getFutasidok_ido_masodik() {
        return futasidok_ido_masodik;
    }

    public void setFutasidok_ido_masodik(String futasidok_ido_masodik) {
        this.futasidok_ido_masodik = futasidok_ido_masodik;
    }

    public String getFutasidok_datum_harmadik() {
        return futasidok_datum_harmadik;
    }

    public void setFutasidok_datum_harmadik(String futasidok_datum_harmadik) {
        this.futasidok_datum_harmadik = futasidok_datum_harmadik;
    }

    public String getFutasidok_ido_harmadik() {
        return futasidok_ido_harmadik;
    }

    public void setFutasidok_ido_harmadik(String futasidok_ido_harmadik) {
        this.futasidok_ido_harmadik = futasidok_ido_harmadik;
    }

    public MotorData() {
        kivantPoz = 0;
        akkuFeszultseg = 0;
        aktualisFordulat = 0;
        kuplungPozicio = "";
        inditastol_eltelt_ido = "";

        futasidok_datum_elso = "";
        futasidok_ido_elso = "";
        futasidok_datum_masodik = "";
        futasidok_ido_masodik = "";
        futasidok_datum_harmadik = "";
        futasidok_ido_harmadik = "";

        futasidok_eredet_elso ="";
        futasidok_eredet_masodik ="";
        futasidok_eredet_harmadik ="";

    }

    public int getKivantPoz() {
        return kivantPoz;
    }

    public void setKivantPoz(int kivantPoz) {
        this.kivantPoz = kivantPoz;
    }

    public double getAkkuFeszultseg() {
        return akkuFeszultseg;
    }

    public void setAkkuFeszultseg(double akkuFeszultseg) {
        this.akkuFeszultseg = akkuFeszultseg;
    }

    public int getAktualisFordulat() {
        return aktualisFordulat;
    }

    public void setAktualisFordulat(int aktualisFordulat) {
        this.aktualisFordulat = aktualisFordulat;
    }

    public String getKuplungPozicio() {
        return kuplungPozicio;
    }

    public void setKuplungPozicio(String kuplungPozicio) {
        this.kuplungPozicio = kuplungPozicio;
    }

    public String getInditastol_eltelt_ido() {
        return inditastol_eltelt_ido;
    }

    public void setInditastol_eltelt_ido(String inditastol_eltelt_ido) {
        this.inditastol_eltelt_ido = inditastol_eltelt_ido;
    }
}
