package com.example.ontozes.communication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.ontozes.data.App;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateData extends AsyncTaskLoader {

    private final App app;

    public UpdateData(@NonNull Context context) {
        super(context);
        app = (App) context.getApplicationContext();
    }

    @Nullable
    @Override
    public Object loadInBackground() {

        try {
            String api_KEY = App.api_KEY;
            URL url = new URL("https://" + App.ACTUAL_DOMAIN + ":1880/param/" + api_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            JSONObject main = new JSONObject(builder.toString());

            //WeatherData
            try{
                JSONObject weather = main.getJSONObject("weather");
                App.weatherData.setHomerseklet(weather.getInt("temp"));
                App.weatherData.setWindSpeed(weather.getInt("windSpeed"));
                App.weatherData.setWindDir(weather.getInt("windDir"));
            }catch ( Exception e){
                System.out.println("\t Kommunikációs hiba! (Időjárás) --> " + e.getMessage() );
            }



            try{
                JSONObject server = main.getJSONObject("server");
                JSONObject parameterek = main.getJSONObject("parameterek");
                JSONObject futasido = main.getJSONObject("futasido");
                JSONObject kuplung = main.getJSONObject("kuplung");
                //Szerver

                App.serverData.setTime(main.getString("time"));
                App.serverData.setSzivattyuKapcsolat(parameterek.getBoolean("kapcsolat"));
                App.serverData.setCpuHomerseklet(server.getDouble("cpuTemp"));


                //Motor

                App.motorData.setKivantPoz(parameterek.getInt("kivantPoz"));
                App.motorData.setAkkuFeszultseg(parameterek.getDouble("akkuFesz"));
                App.motorData.setAktualisFordulat(parameterek.getInt("aktFordulat"));
                App.motorData.setKuplungPozicio(kuplung.getString("pozicio"));
                App.motorData.setInditastol_eltelt_ido(futasido.getString("inditastol"));


                try{
                    JSONObject utolsofutasidok = futasido.getJSONObject("utolsofutasidok");
                    JSONObject elso = utolsofutasidok.getJSONObject("elso");
                    JSONObject masodik = utolsofutasidok.getJSONObject("masodik");
                    JSONObject harmadik = utolsofutasidok.getJSONObject("harmadik");
                    App.motorData.setFutasidok_datum_elso(elso.getString("datum"));
                    App.motorData.setFutasidok_ido_elso(elso.getString("ido"));
                    App.motorData.setFutasidok_eredet_elso(elso.getString("eredet"));

                    App.motorData.setFutasidok_datum_masodik(masodik.getString("datum"));
                    App.motorData.setFutasidok_ido_masodik(masodik.getString("ido"));
                    App.motorData.setFutasidok_eredet_masodik(masodik.getString("eredet"));

                    App.motorData.setFutasidok_datum_harmadik(harmadik.getString("datum"));
                    App.motorData.setFutasidok_ido_harmadik(harmadik.getString("ido"));
                    App.motorData.setFutasidok_eredet_harmadik(harmadik.getString("eredet"));

                }catch ( Exception e){
                    System.out.println("\t Kommunikációs hiba! (Futásidők) --> " + e.getMessage() );
                }


                //Olaj
                App.olajData.setNyomas(parameterek.getDouble("olajNyomas"));
                App.olajData.setGazolajSzint(parameterek.getInt("gazolajSzint"));
                App.olajData.setHomerseklet(parameterek.getDouble("olajHo"));

                //Víz

                App.vizData.setHomerseklet(parameterek.getDouble("vizHo"));
                App.vizData.setNyomas(parameterek.getDouble("vizNyomas"));
                App.vizData.setAramlas(parameterek.getBoolean("aramlas"));
                App.vizData.setVegyszeradagolas(parameterek.getBoolean("vegyszeradagolas"));

                //Dob
                JSONObject dob = main.getJSONObject("dob");
                App.dobData.setKapcsolat(dob.getBoolean("kapcsolat"));
                App.dobData.setUzemmod(dob.getString("uzemmod"));
                App.dobData.setImpulzusok(dob.getInt("impulzusok"));
                App.dobData.setHelyzet(dob.getDouble("helyzet"));
                App.dobData.setNyomas(dob.getDouble("nyomas"));
                App.dobData.setSebesseg(dob.getDouble("sebesseg"));

            }catch ( Exception e){
                System.out.println("\t Kommunikációs hiba! (Paraméterek/Szerver) --> " + e.getMessage() );
            }

            //Lineár
            try {
                JSONObject linear = main.getJSONObject("linear");
                JSONObject gps = linear.getJSONObject("gps");

                App.linearData.setKapcsolat(linear.getBoolean("kapcsolat"));
                App.linearData.setUzemmod(linear.getString("uzemmod"));
                App.linearData.setIrany(linear.getBoolean("irany"));

                App.linearData.setOntozesi_mennyiseg(linear.getDouble("ontozesi_mennyiseg"));

                App.linearData.setSebesseg(linear.getDouble("sebesseg_mperperc"));
                App.linearData.setTeljes_tavolsag(linear.getDouble("teljes_tavolsag"));
                App.linearData.setAktualis_tavolsag_kezdettol(linear.getDouble("aktualis_tavolsag_kezdettol"));
                App.linearData.setViz_nyomas(linear.getDouble("nyomas"));

                App.linearData.setVilagitas(linear.getBoolean("vilagitas"));
                App.linearData.setElso_kocsi(linear.getBoolean("elso_kocsi"));
                App.linearData.setUtolso_kocsi(linear.getBoolean("utolso_kocsi"));


                App.linearData.setLon(gps.getDouble("lon"));
                App.linearData.setLat(gps.getDouble("lat"));
                App.linearData.setKezdet_lon(gps.getDouble("kezdet_lon"));
                App.linearData.setKezdet_lat(gps.getDouble("kezdet_lat"));
                App.linearData.setVege_lon(gps.getDouble("vege_lon"));
                App.linearData.setVege_lat(gps.getDouble("vege_lat"));

            }catch ( Exception e){
                System.out.println("\t Kommunikációs hiba! (Lineár) --> " + e.getMessage() );
            }


            urlConnection.disconnect();
        } catch (IOException | JSONException e) {
            app.changeDomain();
            e.printStackTrace();

        }

        return app;
    }

    @Override
    public void deliverResult(@Nullable Object app) {
        super.deliverResult(app);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }
}
