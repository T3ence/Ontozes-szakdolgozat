package com.example.ontozes.communication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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

public class SendCommand extends AsyncTask<String, String, Boolean> {

    private String[] data;
    private final Context context;
    private final String parancs;
    private final SmsManager smsManager;
    private final Activity activity;

    public SendCommand( Activity activity, Context context, String parancs) {
        //set context variables if required
        this.context = context;
        // app = (App) context.getApplicationContext();
        this.parancs = parancs;
        this.activity = activity;
        this.smsManager = SmsManager.getDefault();
    }

    @Override
    protected Boolean doInBackground(String... param) {
        boolean vissza = false;
        try {
            String api_KEY = App.api_KEY;
            URL url = new URL("https://" + App.ACTUAL_DOMAIN + ":1880/" + parancs + "/" + api_KEY);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();

            String inputString;
            while ((inputString = bufferedReader.readLine()) != null) {
                builder.append(inputString);
            }

            JSONObject main = new JSONObject(builder.toString());
            vissza = main.getBoolean("get");

            urlConnection.disconnect();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return vissza;
    }


    @Override
    protected void onPostExecute(Boolean b) {
        super.onPostExecute(b);
        String msg = "";
        switch (parancs) {
            case "leallitas":
                if (b) {
                    msg = "Szivatty?? le??ll??tva";
                } else {
                    msg = "Probl??ma a szivatt?? le??ll??t??sa sor??n.\n                      Sms k??ldve.";
                    SendSms(App.TELEFONSZAM_SZIVATTYU, "Leallitas");

                }
                break;
            case "alapjarat":
                if (b) {
                    msg = "Szivatty?? alapj??ratba helyezve";
                } else {
                    msg = "Probl??ma az alapj??ratba helyez??s sor??n.\n                      Sms k??ldve.";
                    SendSms(App.TELEFONSZAM_SZIVATTYU, "Alapjarat");
                }
                break;
            case "ontozes":
                if (b) {
                    msg = "??nt??z??s ind??tva";
                } else {
                    msg = "Probl??ma az ??nt??z??s ind??t??sa sor??n.\n                      Sms k??ldve.";
                    SendSms(App.TELEFONSZAM_SZIVATTYU, "Inditas");
                }
                break;
            case "plus":
                if (b) {
                    msg = "G??z n??velve";
                } else {
                    msg = "Probl??ma a g??z n??vel??se sor??n";
                }
                break;
            case "minus":
                if (b) {
                    msg = "G??z cs??kkentve";
                } else {
                    msg = "Probl??ma a g??z cs??kkent??se sor??n";
                }
                break;
            case "arduinoreset":
                if (b) {
                    msg = "Arduino ??jraind??tva";
                } else {
                    msg = "Probl??ma az arduino ??jraind??t??sa sor??n";
                }
                break;
            case "inditas_elore":
                if (b) {
                    msg = "Line??r el??re ir??nyban";
                } else {
                    msg = "Probl??ma a line??r ir??nyba ??ll??t??sa sor??n";
                }
                break;
            case "inditas_hatra":
                if (b) {
                    msg = "Line??r h??tra ir??nyban";
                } else {
                    msg = "Probl??ma a line??r ir??nyba ??ll??t??sa sor??n";
                }
                break;
            case "linear_leallitas":
                if (b) {
                    msg = "Line??r le??ll??tva";
                } else {
                    msg = "Probl??ma a line??r le??ll??t??sa sor??n";
                }
                break;
            default:
                msg = "ERROR!";
                break;
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public void SendSms(String kTelefonszam, String msg) {

        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

            smsManager.sendTextMessage(kTelefonszam, null, msg, null, null);

        } else {
            //Toast.makeText(this, "Enged??lyezd az SMS hozz??f??r??st az alkalmaz??snak!", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions( activity , new String[]{Manifest.permission.SEND_SMS}, 0);

        }
    }


}
