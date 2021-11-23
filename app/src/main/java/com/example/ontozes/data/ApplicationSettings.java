package com.example.ontozes.data;

public class ApplicationSettings {

    private boolean actualTheme;

    public ApplicationSettings() {
        this.actualTheme = false;
    }

    public boolean isActualTheme() {
        return actualTheme;
    }

    public void setActualTheme(boolean actualTheme) {
        this.actualTheme = actualTheme;
    }
}
