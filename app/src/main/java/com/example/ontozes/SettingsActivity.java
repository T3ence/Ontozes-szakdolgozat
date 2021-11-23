package com.example.ontozes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    private static final  String SHARED_PREF_NAME = "sharedprefs";
    public static final String THEME_STATUS_SP = "tema";

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

    private ImageButton settings_back_bt;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch tema_kezzel;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch ejszakai_tema_sw;
    private ConstraintLayout ejszakai_tema_category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                //.replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        settings_back_bt = findViewById(R.id.settings_back_bt);
        tema_kezzel = findViewById(R.id.tema_kezzel);
        ejszakai_tema_sw = findViewById(R.id.ejszakai_tema_sw);
        ejszakai_tema_category = findViewById(R.id.ejszakai_tema_category);

        loadSettings();

        tema_kezzel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                sharedpreferences = PreferenceManager.getDefaultSharedPreferences( getApplicationContext() );
                SharedPreferences.Editor editor = sharedpreferences.edit();

                if(  isChecked ){
                    editor.putBoolean( "tema_kezzel" , true);
                    editor.apply();
                }else{
                    editor.putBoolean( "tema_kezzel" , false);
                    editor.apply();
                }

                loadSettings();

            }
        });

        ejszakai_tema_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position

                sharedpreferences = PreferenceManager.getDefaultSharedPreferences( getApplicationContext() );
                SharedPreferences.Editor editor = sharedpreferences.edit();

                if(  isChecked ){
                    editor.putBoolean( "tema" , true);
                    editor.apply();
                    recreate();
                }else{
                    editor.putBoolean( "tema" , false);
                    editor.apply();
                    recreate();
                }

                loadSettings();

            }
        });

    }


    private void loadSettings(){
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ejszakai_tema_sw.setChecked( sharedpreferences.getBoolean("tema",false) );
        tema_kezzel.setChecked( sharedpreferences.getBoolean("tema_kezzel",false) );

        if( !tema_kezzel.isChecked() ){
            ejszakai_tema_category.setAlpha( (float)0.4 );
            ejszakai_tema_category.setEnabled(false);
            ejszakai_tema_sw.setClickable(false);
        }else{
            ejszakai_tema_category.setAlpha( (float)1 );
            ejszakai_tema_category.setEnabled(true);
            ejszakai_tema_sw.setClickable(true);
        }

    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void vissza(View view) {
        Toast.makeText(getApplicationContext(), "Beállítások mentve", Toast.LENGTH_LONG).show();

        settings_back_bt.setBackgroundResource(R.drawable.ic_left_arrow_greenlight);

        finish();
    }
/*
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

 */
    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        if( GetTheme() ){
            setTheme(R.style.App_Theme_Dark);
            SaveTheme(true);
        }else{
            setTheme(R.style.Semmi);
            SaveTheme(false);
        }

        return super.onCreateView(name, context, attrs);
    }


    

}