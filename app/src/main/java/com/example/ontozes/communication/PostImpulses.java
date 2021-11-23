package com.example.ontozes.communication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.ontozes.data.App;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public class PostImpulses extends AsyncTask<Double, String, Integer> {

    private final double kivantHelyzet;
    private Context context;


    public PostImpulses(double kivantHelyzet, Context context) {
        this.context = context;
        this.kivantHelyzet = kivantHelyzet;
        //app = (App) context.getApplicationContext();

    }

    @Override
    protected Integer doInBackground(Double... doubles) {

        int impulzusok = meterToImpulse(kivantHelyzet);
        String data = Integer.toString(impulzusok); //data to post
        HttpURLConnection urlConnection = null;
        try {
            String api_KEY = App.api_KEY;
            URL url = new URL("https://" + App.ACTUAL_DOMAIN +":1880/remoteArduino/" + api_KEY);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/String");
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out, StandardCharsets.UTF_8));
            writer.write(data);
            writer.flush();

            int code = urlConnection.getResponseCode();
            if (code != 200) {
                throw new IOException("Invalid response from server: " + code);
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                Log.i("data", line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return impulzusok;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Toast.makeText(context.getApplicationContext(), integer + " db impulzus = " + new DecimalFormat("##.##").format(getHelyzet(integer)) + " m", Toast.LENGTH_LONG).show();

    }

    int meterToImpulse(double meter) {
        int result = 0;
        int i;
        for (i = 0; meter - getHelyzet(i) > 0; i++) {
            result++;
        }

        if (Math.abs((meter - getHelyzet(i))) > Math.abs((meter - getHelyzet(i - 1)))) {
            result--;
        }

        return result;
    }

    //from arduino!
    private double getHelyzet(int aktualisTic) {

        int elsoKezdoTic = 8300;
        int elsoVegzoTic = 10500;
        int elsoHosszaTic = 2200;
        double elsoTicPerMeter = 0.02775;

        int masodikKezdoTic = 6100;
        int masodikVegzoTic = 8300;
        int masodikHosszaTic = 2200;
        double masodikTicPerMeter = 0.0315;

        int harmadikKezdoTic = 3900;
        int harmadikVegzoTic = 6100;
        int harmadikHosszaTic = 2200;
        double harmadikTicPerMeter = 0.03455;

        int negyedikKezdoTic = 1700;
        int negyedikVegzoTic = 3900;
        int negyedikHosszaTic = 2200;
        double negyedikTicPerMeter = 0.038;

        int otodikKezdoTic = 0;
        int otodikVegzoTic = 1700;
        int otodikHosszaTic = 1700;
        double otodikTicPerMeter = 0.0414;

        int teljesHosszTic = 10500;

        double meter = 0;

        //Otodik Sor
        if (aktualisTic >= otodikKezdoTic && aktualisTic <= otodikVegzoTic) {
            meter = meter + aktualisTic * otodikTicPerMeter;

            //Negyedik Sor
        } else if (aktualisTic > negyedikKezdoTic && aktualisTic <= negyedikVegzoTic) {
            meter = meter + otodikHosszaTic * otodikTicPerMeter;
            meter = meter + (aktualisTic - otodikHosszaTic) * negyedikTicPerMeter;

            //Harmadik Sor
        } else if (aktualisTic > harmadikKezdoTic && aktualisTic <= harmadikVegzoTic) {
            meter = meter + otodikHosszaTic * otodikTicPerMeter;
            meter = meter + negyedikHosszaTic * negyedikTicPerMeter;
            meter = meter + (aktualisTic - (otodikHosszaTic + negyedikHosszaTic)) * harmadikTicPerMeter;

            //Mosodik Sor
        } else if (aktualisTic > masodikKezdoTic && aktualisTic <= masodikVegzoTic) {
            meter = meter + otodikHosszaTic * otodikTicPerMeter;
            meter = meter + negyedikHosszaTic * negyedikTicPerMeter;
            meter = meter + harmadikHosszaTic * harmadikTicPerMeter;
            meter = meter + (aktualisTic - (otodikHosszaTic + negyedikHosszaTic + harmadikHosszaTic)) * masodikTicPerMeter;

            //Elso Sor
        } else if (aktualisTic > elsoKezdoTic && aktualisTic <= elsoVegzoTic) {
            meter = meter + otodikHosszaTic * otodikTicPerMeter;
            meter = meter + negyedikHosszaTic * negyedikTicPerMeter;
            meter = meter + harmadikHosszaTic * harmadikTicPerMeter;
            meter = meter + masodikHosszaTic * masodikTicPerMeter;
            meter = meter + (aktualisTic - (otodikHosszaTic + negyedikHosszaTic + harmadikHosszaTic + masodikHosszaTic)) * elsoTicPerMeter;
        } else if (aktualisTic > teljesHosszTic) {
            meter = 361;
        } else if (aktualisTic < otodikKezdoTic) {
            meter = 0;
        }
        return meter;
    }


}
