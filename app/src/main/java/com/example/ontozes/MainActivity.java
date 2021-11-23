package com.example.ontozes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;

import com.example.ontozes.communication.ApiKeyDialog;
import com.example.ontozes.communication.ArduinoRestartDialog;
import com.example.ontozes.communication.HelyzetDialog;
import com.example.ontozes.communication.PostImpulses;
import com.example.ontozes.communication.SendCommand;
import com.example.ontozes.data.App;
import com.example.ontozes.communication.UpdateData;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ontozes.ui.main.SectionsPagerAdapter;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks, ArduinoRestartDialog.ArduinoRestartDialogListener, HelyzetDialog.HelyzetDialogListener, ApiKeyDialog.ApiKeyDialogListener {

    private App app;
    private Handler handler = new Handler();
    private TextView temp;
    private TextView connection_circle;
    private int cnt_connection_circle_presses = 0;
    private TextView windSpeed;
    private ImageView compass;

    private FloatingActionsMenu floatingActionsMenu;

    private Button plusFordulatB;
    private Button minusFordulatB;

    private Button linearEloreB;
    private Button linearHatraB;
    private Button linearLeallitasB;

    private CardView kamera_button;

    private ViewPager mViewPager;
    private Activity activity;

    private SmsManager smsManager;

    private TextView server_name;
    private TextView title;

    private boolean futasidok_pressed = false;

    private ImageView server_img;

    private ImageView wind_img;
    private ImageView temp_img;

    private CardView futasidok_card;

    private Button settings_bt;
    SharedPreferences sharedpreferences;

    private static final  String SHARED_PREF_NAME = "sharedprefs";
    public static final String THEME_STATUS_SP = "tema";
    public static final String THEME_MANUAL_SP = "tema_kezzel";

    public boolean GetTheme(){
        boolean status = false;
        //sharedpreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext() );

        if (sharedpreferences.contains(THEME_STATUS_SP)) {
            status = sharedpreferences.getBoolean(THEME_STATUS_SP, false);
        }
        return status;
    }

    public void SaveTheme(boolean status) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(THEME_STATUS_SP, status);
        editor.apply();
    }

    public void dynamicTheme(){
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean status = false;
        if (sharedpreferences.contains(THEME_MANUAL_SP)) {
            status = sharedpreferences.getBoolean(THEME_MANUAL_SP, false);
        }

        if(!status){
            // Pass the time zone display here in the second parameter.
            Calendar[] sunriseSunset = ca.rmen.sunrisesunset.SunriseSunset.getSunriseSunset(Calendar.getInstance(), 46.22470, 18.59069);
            System.out.println("--------------------------------------> Sunrise at: " + sunriseSunset[0].getTime());
            System.out.println("--------------------------------------> Sunset at: " + sunriseSunset[1].getTime());

            Date sunRise = sunriseSunset[0].getTime();
            Date sunSet = sunriseSunset[1].getTime();
            Date currentTime = Calendar.getInstance().getTime();

            System.out.println("--------------------------------------> Current Time is at: " + currentTime );
            if( currentTime.after(sunSet) || currentTime.before(sunRise) ){
                setTheme(R.style.App_Theme_Dark);
                SaveTheme(true);
                System.out.println("--------------------------------------> Dark Theme set");

            }else{
                setTheme(R.style.Semmi);
                SaveTheme(false);
                System.out.println("--------------------------------------> Light Theme set");
            }

        }else {
            if( GetTheme() ){
                setTheme(R.style.App_Theme_Dark);
                SaveTheme(true);
            }else{
                setTheme(R.style.Semmi);
                SaveTheme(false);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Make sure this is before calling super.onCreate
        disableScreenZoomNew();
        disableScreenZoom();

        app = (App) getApplicationContext();

        dynamicTheme();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        //disableScreenZoom();
        //disableScreenZoomNew();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        Animation fade_in = AnimationUtils.loadAnimation( getApplicationContext() , R.anim.fade_in_long);
        Animation slide_down = AnimationUtils.loadAnimation( getApplicationContext() , R.anim.slide_down);

        floatingActionsMenu = findViewById(R.id.flaoting_action_menu);
        floatingActionsMenu.startAnimation(fade_in);


        //app = (App) getApplicationContext();
        handler.postDelayed(runnable, 20);

        title = findViewById(R.id.title);
        title.setVisibility(View.INVISIBLE);
        title.startAnimation(slide_down);
        title.setVisibility(View.VISIBLE);

        connection_circle = findViewById(R.id.connection_circle);
        connection_circle.setVisibility(View.INVISIBLE);
        connection_circle.setBackgroundResource(R.drawable.disconnected);
        connection_circle.startAnimation(slide_down);
        connection_circle.setVisibility(View.VISIBLE);

        futasidok_card = findViewById(R.id.futasidok_card);

        server_img = findViewById(R.id.server_img);
        server_img.setVisibility(View.INVISIBLE);
        server_img.startAnimation(slide_down);
        server_img.setVisibility(View.VISIBLE);

        server_name = findViewById(R.id.server_name);
        server_name.setVisibility(View.INVISIBLE);
        server_name.startAnimation(slide_down);
        server_name.setVisibility(View.VISIBLE);

        compass = findViewById(R.id.compass);
        compass.setVisibility(View.INVISIBLE);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation sd0 = AnimationUtils.loadAnimation( getApplicationContext() , R.anim.slide_down);
                compass.startAnimation(sd0);
                compass.setVisibility(View.VISIBLE);
            }
        }, 100);



        wind_img = findViewById(R.id.wind_img);
        wind_img.setVisibility(View.INVISIBLE);

        windSpeed = findViewById(R.id.windSpeed);
        windSpeed.setVisibility(View.INVISIBLE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation sd1 = AnimationUtils.loadAnimation( getApplicationContext() , R.anim.slide_down);
                wind_img.startAnimation(sd1);
                wind_img.setVisibility(View.VISIBLE);
                windSpeed.startAnimation(sd1);
                windSpeed.setVisibility(View.VISIBLE);
            }
        }, 500);



        temp_img = findViewById(R.id.temp_img);
        temp_img.setVisibility(View.INVISIBLE);

        temp = findViewById(R.id.temp);
        temp.setText("");
        temp.setVisibility(View.INVISIBLE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation sd2 = AnimationUtils.loadAnimation( getApplicationContext() , R.anim.slide_down);

                temp_img.startAnimation(sd2);
                temp_img.setVisibility(View.VISIBLE);

                temp.startAnimation(sd2);
                temp.setVisibility(View.VISIBLE);
            }
        }, 1000);


        settings_bt = findViewById(R.id.settings_bt);
        settings_bt.setVisibility(View.INVISIBLE);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation sd3 = AnimationUtils.loadAnimation( getApplicationContext() , R.anim.slide_down);

                settings_bt.startAnimation(sd3);
                settings_bt.setVisibility(View.VISIBLE);
            }
        }, 1200);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

        //°C
        smsManager = SmsManager.getDefault();

        ImageView bauer_logo = findViewById(R.id.bauer_logo);
        kamera_button = findViewById(R.id.kamera_button);

        // READ API KEY
        if (app.loadApi()) {
            Toast.makeText(getApplicationContext(), "Módosított API Kulcs használata", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (floatingActionsMenu.isExpanded()) {

                Rect outRect = new Rect();
                floatingActionsMenu.getGlobalVisibleRect(outRect);

                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY()))
                    floatingActionsMenu.collapse();
            }
        }

        return super.dispatchTouchEvent(event);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refresh();
            handler.postDelayed(this, 1000);
        }
    };

    @SuppressLint("SetTextI18n")
    public void refresh() {
        getSupportLoaderManager().restartLoader(0, null, this);
        temp.setText((int) App.weatherData.getHomerseklet() + " °C");
        windSpeed.setText(App.weatherData.getWindSpeed() + " km/h");
        setCompass(App.weatherData.getWindDir());

        if( App.ACTUAL_DOMAIN.equals(App.PIKSZO_DOMAIN) ){
            server_name.setText("Dusnok");
        }else{
            server_name.setText("Szeged");
        }

        if (App.serverData.isSzivattyuKapcsolat()) {
            connection_circle.setBackgroundResource(R.drawable.connected);
        } else {
            connection_circle.setBackgroundResource(R.drawable.disconnected);
        }
    }

    private void setCompass(int direction) {
        if (direction > 328 || direction <= 22) {
            compass.setImageResource(R.drawable.ic_del);
        } else if (22 < direction && direction <= 68) {
            compass.setImageResource(R.drawable.ic_delnyugat);
        } else if (68 < direction && direction <= 112) {
            compass.setImageResource(R.drawable.ic_nyugat);
        } else if (112 < direction && direction <= 158) {
            compass.setImageResource(R.drawable.ic_eszaknyugat);
        } else if (158 < direction && direction <= 202) {
            compass.setImageResource(R.drawable.ic_eszak);
        } else if (202 < direction && direction <= 248) {
            compass.setImageResource(R.drawable.ic_eszakkelet);
        } else if (248 < direction && direction <= 292) {
            compass.setImageResource(R.drawable.ic_kelet);
        } else if (292 < direction && direction <= 328) {
            compass.setImageResource(R.drawable.ic_delkelet);
        }


    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        return new UpdateData(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object app) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void plusFord(View v) {
        new SendCommand(this ,getApplicationContext(), "plus").execute();
        Button plusFordulat = v.findViewById(R.id.plusFordulat);

        Animation blink = AnimationUtils.loadAnimation( getApplicationContext() , R.anim.blink);
        plusFordulat.startAnimation(blink);

        plusFordulatB = plusFordulat;

        plusFordulat.setBackground(getDrawable(R.drawable.button_bck_green));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                plusFordulatB.setBackground(getDrawable(R.drawable.button_bck_orange));
            }
        }, 1000);

    }

    public void minusFord(View v) {
        new SendCommand(this, getApplicationContext(), "minus").execute();
        Button minusFordulat = v.findViewById(R.id.minusFordulat);

        Animation blink = AnimationUtils.loadAnimation( getApplicationContext() , R.anim.blink);
        minusFordulat.startAnimation(blink);

        minusFordulatB = minusFordulat;

        minusFordulat.setBackground(getDrawable(R.drawable.button_bck_green));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                minusFordulatB.setBackground(getDrawable(R.drawable.button_bck_orange));
            }
        }, 1000);
    }

    public void leallitas(View view) {
        new SendCommand( this, getApplicationContext()  , "leallitas").execute();
        floatingActionsMenu.collapse();
    }

    public void alapjarat(View view) {
        new SendCommand(this, getApplicationContext(), "alapjarat").execute();
        floatingActionsMenu.collapse();
    }

    public void ontozes(View view) {
        new SendCommand(this, getApplicationContext(), "ontozes").execute();
        floatingActionsMenu.collapse();
    }

    public void inditas_elore(View view) {
        new SendCommand(this, getApplicationContext(), "inditas_elore").execute();
        Button iranyElore = view.findViewById(R.id.linearElore);

        Animation blink = AnimationUtils.loadAnimation( getApplicationContext() , R.anim.blink);
        iranyElore.startAnimation(blink);

        linearEloreB = iranyElore;

        iranyElore.setBackground(getDrawable(R.drawable.button_bck_green));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                linearEloreB.setBackground(getDrawable(R.drawable.button_bck_orange));
            }
        }, 1000);
    }

    public void inditas_hatra(View view) {
        new SendCommand(this, getApplicationContext(), "inditas_hatra").execute();
        Button iranyHatra = view.findViewById(R.id.linearHatra);

        Animation blink = AnimationUtils.loadAnimation( getApplicationContext() , R.anim.blink);
        iranyHatra.startAnimation(blink);

        linearHatraB = iranyHatra;

        iranyHatra.setBackground(getDrawable(R.drawable.button_bck_green));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                linearHatraB.setBackground(getDrawable(R.drawable.button_bck_orange));
            }
        }, 1000);
    }


    public void linear_leallitas(View view) {
        new SendCommand(this, getApplicationContext(), "linear_leallitas").execute();
        Button linearLeallitas = view.findViewById(R.id.linearLeallitas);

        Animation blink = AnimationUtils.loadAnimation( getApplicationContext() , R.anim.blink);
        linearLeallitas.startAnimation(blink);

        linearLeallitasB = linearLeallitas;

        linearLeallitas.setBackground(getDrawable(R.drawable.button_bck_green));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLeallitasB.setBackground(getDrawable(R.drawable.button_bck_orange));
            }
        }, 1000);
    }

    public void connection_circle_pressed(View view) {
        if (cnt_connection_circle_presses > 4) {
            cnt_connection_circle_presses = 0;

            //Ide kellene egy dialógus
            ApiKeyDialog apiKeyDialog = new ApiKeyDialog();
            apiKeyDialog.show(getSupportFragmentManager(), "API kulcs megadása");

        } else {

            if (cnt_connection_circle_presses != 3 && cnt_connection_circle_presses != 5 && cnt_connection_circle_presses > 1) {
                Toast.makeText(getApplicationContext(), cnt_connection_circle_presses + "", Toast.LENGTH_SHORT).show();
            }

            if (cnt_connection_circle_presses == 3) {
                Toast.makeText(getApplicationContext(), "Aktuális kulcs: " + App.api_KEY, Toast.LENGTH_LONG).show();
            }
            cnt_connection_circle_presses++;
        }
    }

    public void disableScreenZoom() {
        Configuration configuration = getResources().getConfiguration();
        configuration.fontScale = (float) 1; //0.85 small size, 1 normal size, 1,15 big etc
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        configuration.densityDpi = (int) getResources().getDisplayMetrics().xdpi;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }

    public void disableScreenZoomNew() {
        Configuration configuration = getResources().getConfiguration();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Resources resources = getBaseContext().getResources();

        Log.d("TAG", metrics.toString() + " " + getResources().getConfiguration());

        if (resources.getDisplayMetrics().xdpi >= 1 && getResources().getDisplayMetrics().xdpi <= 640) {
            configuration.densityDpi = 422;
        }
/*
        if ( resources.getDisplayMetrics().xdpi >= 1 && getResources().getDisplayMetrics().xdpi <= 120  ) { //change size to ignore display size
                configuration.densityDpi = 120;
        }else if (getResources().getDisplayMetrics().xdpi >= 121 && getResources().getDisplayMetrics().xdpi <= 160 ) {
                configuration.densityDpi = 160;
        }else if (getResources().getDisplayMetrics().xdpi >= 161 && getResources().getDisplayMetrics().xdpi <= 240 ) {
            configuration.densityDpi = 240;
        }else if (getResources().getDisplayMetrics().xdpi >= 241 && getResources().getDisplayMetrics().xdpi <= 320 ) {
            configuration.densityDpi = 320;
        }else if (getResources().getDisplayMetrics().xdpi >= 321 && getResources().getDisplayMetrics().xdpi <= 480 ) {
            configuration.densityDpi = 480;
        }else if (getResources().getDisplayMetrics().xdpi >= 481 && getResources().getDisplayMetrics().xdpi <= 640 ) {
            configuration.densityDpi = 640;
        }
        */
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }

    public void onClickHelyzet(View view) {
        HelyzetDialog helyzetDialog = new HelyzetDialog();
        helyzetDialog.show(getSupportFragmentManager(), "helyzet dialógus");
    }

    @Override
    public void applyHelyzet(String rHelyzetString) {
        final double kivantHelyzet = Double.parseDouble(rHelyzetString);

        if (kivantHelyzet > 361 || kivantHelyzet < 0) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), kivantHelyzet + " m-hez nem elég hosszú a cső", Toast.LENGTH_LONG).show();
                }
            }, 200);
        } else {
            new PostImpulses(kivantHelyzet, getApplicationContext()).execute();
        }
    }



    @Override
    public void applyApiKey(final String apiKey) {
        App.api_KEY = apiKey;
        app.saveApi(apiKey);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "A kulcs kicserélve erre: " + App.api_KEY, Toast.LENGTH_LONG).show();
            }
        }, 200);

    }



    public void kamera_bt_pressed(final View view) {
        final CardView kamera_button = view.findViewById(R.id.kamera_button);

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = view.getContext().getTheme();
        theme.resolveAttribute(R.attr.click_indicator, typedValue, true);
        @ColorInt int color = typedValue.data;

        kamera_button.setCardBackgroundColor( color );

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = view.getContext().getTheme();
                theme.resolveAttribute(R.attr.colorAccentTransparentLight, typedValue, true);
                @ColorInt int color = typedValue.data;

                kamera_button.setCardBackgroundColor( color );
            }
        }, 200);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                app.openipcamera(view);
            }
        }, 300);
    }


    public void futasidok(final View view){
        //Toast.makeText(getApplicationContext(), "KAKAS", Toast.LENGTH_LONG).show();
        final CardView futasidok_card = findViewById(R.id.futasidok_card);

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = view.getContext().getTheme();
        theme.resolveAttribute(R.attr.click_indicator, typedValue, true);
        @ColorInt int color = typedValue.data;

        futasidok_card.setCardBackgroundColor( color );

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void run() {

                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = view.getContext().getTheme();
                theme.resolveAttribute(R.attr.colorAccentTransparentLight, typedValue, true);
                @ColorInt int color = typedValue.data;

                futasidok_card.setCardBackgroundColor( color );

            }
        }, 200);

        if (futasidok_pressed){
            Animation slideUp = AnimationUtils.loadAnimation( view.getContext() , R.anim.slide_up);
            LinearLayout linearLayout = findViewById(R.id.futasidok_tab);
            linearLayout.startAnimation(slideUp);
            linearLayout.setVisibility(View.INVISIBLE);
            futasidok_pressed = false;
        }else{
            Animation slideUp = AnimationUtils.loadAnimation( view.getContext() , R.anim.slide_down);
            LinearLayout linearLayout = findViewById(R.id.futasidok_tab);
            linearLayout.startAnimation(slideUp);
            linearLayout.setVisibility(View.VISIBLE);
            futasidok_pressed = true;
        }
    }


    @Override
    public void applyArduinoRestart() {
        new SendCommand(this ,getApplicationContext(), "arduinoreset").execute();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    public void settings_bt_pressed(View view) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

        settings_bt.setBackgroundResource(R.drawable.ic_settings_green);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                settings_bt.setBackgroundResource(R.drawable.ic_settings_white);
            }
        }, 200);
    }
}