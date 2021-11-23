package com.example.ontozes.data;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import com.example.ontozes.Dob;
import com.example.ontozes.Linear;
import com.example.ontozes.Parameterek;
import com.example.ontozes.Vezerles;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class App extends Application {
    public static WeatherData weatherData = new WeatherData();
    public static MotorData motorData = new MotorData();
    public static OlajData olajData = new OlajData();
    public static DobData dobData = new DobData();
    public static VizData vizData = new VizData();
    public static ServerData serverData = new ServerData();
    public static LinearData linearData = new LinearData();

    public static String FILE_NAME = "apikey.txt";
    public static String api_KEY = "adat";

    public static String TELEFONSZAM_SZIVATTYU = "";

    public static String PIKSZO_DOMAIN = "pikszo.ddns.net";
    public static String THINKBOX_DOMAIN = "thinkbox.ddns.net";
    public static String ACTUAL_DOMAIN = PIKSZO_DOMAIN;


    private static final  String SHARED_PREF_NAME = "sharedprefs";
    public static final String THEME_STATUS_SP = "theme";


    public static ApplicationSettings settings;

    public App() {}

    public boolean Theme(){
        boolean b = false;
        try{
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            b = sharedPreferences.getBoolean(THEME_STATUS_SP,false);
        }catch (Exception e){
            System.out.println("\t Probláma az App.Theme()-ben" + e.getMessage());
        }
        return  b;
    }

    public void saveTheme(boolean status){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.apply();
        editor.putBoolean(THEME_STATUS_SP, status);
    }

    public void changeDomain(){
        if( ACTUAL_DOMAIN.equals(PIKSZO_DOMAIN) ){
            ACTUAL_DOMAIN = THINKBOX_DOMAIN;
        }else{
            ACTUAL_DOMAIN = PIKSZO_DOMAIN;
        }
    }

    public void saveApi(String api_KEY) {
        App.api_KEY = api_KEY;
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(App.FILE_NAME, MODE_PRIVATE);
            fos.write(api_KEY.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public boolean loadApi() {
        boolean rendben = false;

        FileInputStream fis = null;
        try {
            fis = openFileInput(App.FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            App.api_KEY = sb.toString();
            rendben = true;
        } catch (IOException e) {
            e.printStackTrace();
            rendben = false;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    rendben = false;
                }
            }
        }

        return rendben;
    }

    public void openipcamera(View view) {
        Intent i = getPackageManager().getLaunchIntentForPackage("com.ycc365plus.aws");
        if (i != null) {
            startActivity(i);
        } else {
            Toast.makeText(view.getContext(), "Kérlek telepítsed az alakalmazást!", Toast.LENGTH_LONG).show();
        }
    }

}



