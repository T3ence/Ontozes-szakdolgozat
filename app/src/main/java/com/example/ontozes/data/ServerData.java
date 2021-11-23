package com.example.ontozes.data;

public class ServerData {
    private String time;
    private boolean szivattyuKapcsolat;
    private double cpuHomerseklet;

    public ServerData() {
        time = "";
        szivattyuKapcsolat = false;
        cpuHomerseklet = 0;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSzivattyuKapcsolat() {
        return szivattyuKapcsolat;
    }

    public void setSzivattyuKapcsolat(boolean szivattyuKapcsolat) {
        this.szivattyuKapcsolat = szivattyuKapcsolat;
    }

    public double getCpuHomerseklet() {
        return cpuHomerseklet;
    }

    public void setCpuHomerseklet(double cpuHomerseklet) {
        this.cpuHomerseklet = cpuHomerseklet;
    }
}
