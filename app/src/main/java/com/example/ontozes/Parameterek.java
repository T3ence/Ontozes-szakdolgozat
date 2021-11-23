package com.example.ontozes;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ontozes.data.App;
import com.github.anastr.speedviewlib.Gauge;
import com.github.anastr.speedviewlib.components.Section;
import com.john.waveview.WaveView;

import java.util.Objects;

public class Parameterek extends Fragment {

    private Gauge olajHoG;
    private Gauge vizHoG;
    private WaveView gazolajSzintG;
    private TextView gazolajszint_text;
    private TextView parameterek_feszultseg_text;

    private TextView parameterek_olajnyomas;
    private TextView parameterek_visznyomas;
    private TextView parameterek_aramlas;
    private TextView parameterek_vagyszer;
    private TextView inditastol_elteltido;

    private Handler handler = new Handler();


    private TextView futasidok_datum_elso;
    private TextView futasidok_ido_elso;

    private TextView futasidok_datum_masodik;
    private TextView futasidok_ido_masodik;

    private TextView futasidok_datum_harmadik;
    private TextView futasidok_ido_harmadik;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private ImageView futasido_1_img;
    private ImageView futasido_2_img;
    private ImageView futasido_3_img;
    private ImageView futasido_1_leallitas_img;
    private ImageView futasido_2_leallitas_img;
    private ImageView futasido_3_leallitas_img;

    public static Parameterek newInstance(String param1, String param2) {
        Parameterek fragment = new Parameterek();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    public Parameterek() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parameterek, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // app = (App) requireContext().getApplicationContext();

        inditastol_elteltido = getView().findViewById(R.id.inditastol_elteltido);
        inditastol_elteltido.setText("00:00");

        olajHoG = getView().findViewById(R.id.olajHo);
        olajHoG.makeSections(5, Color.argb(90, 17, 232, 124), Section.Style.SQUARE);
        olajHoG.speedTo(0);


        vizHoG = getView().findViewById(R.id.vizHo);
        vizHoG.makeSections(5, Color.argb(90, 17, 232, 124), Section.Style.SQUARE);
        vizHoG.speedTo(0);

        gazolajSzintG = getView().findViewById(R.id.gazolajszint_gauge);
        gazolajSzintG.setProgress(0);
        gazolajszint_text = getView().findViewById(R.id.gazolajszint_text);
        gazolajszint_text.setText(0 + "%");


        parameterek_feszultseg_text = getView().findViewById(R.id.parameterek_feszultseg_text);
        parameterek_feszultseg_text.setText(0 + " V");

        parameterek_olajnyomas = getView().findViewById(R.id.parameterek_olajnyomas);
        parameterek_olajnyomas.setText(0 + " Bar");
        parameterek_visznyomas = getView().findViewById(R.id.parameterek_visznyomas);
        parameterek_visznyomas.setText(0 + " Bar");
        parameterek_aramlas = getView().findViewById(R.id.parameterek_aramlas);
        parameterek_aramlas.setText("Nincs");
        parameterek_vagyszer = getView().findViewById(R.id.parameterek_vagyszer);
        parameterek_vagyszer.setText("Nincs");

        //--------------------------
        futasidok_datum_elso = getView().findViewById(R.id.futasidok_datum_elso);
        futasidok_ido_elso = getView().findViewById(R.id.futasidok_ido_elso);
        futasido_1_img = getView().findViewById(R.id.futasido_1_img);
        futasido_1_leallitas_img = getView().findViewById(R.id.futasido_1_leallitas_img);

        futasidok_datum_masodik = getView().findViewById(R.id.futasidok_datum_masodik);
        futasidok_ido_masodik = getView().findViewById(R.id.futasidok_ido_masodik);
        futasido_2_img = getView().findViewById(R.id.futasido_2_img);
        futasido_2_leallitas_img = getView().findViewById(R.id.futasido_2_leallitas_img);

        futasidok_datum_harmadik = getView().findViewById(R.id.futasidok_datum_harmadik);
        futasidok_ido_harmadik = getView().findViewById(R.id.futasidok_ido_harmadik);
        futasido_3_img = getView().findViewById(R.id.futasido_3_img);
        futasido_3_leallitas_img = getView().findViewById(R.id.futasido_3_leallitas_img);


        handler.postDelayed(runnable, 20);


    }

    @SuppressLint("SetTextI18n")
    private void refreshParameterek() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                olajHoG.speedTo((float) App.olajData.getHomerseklet());
                vizHoG.speedTo((float) App.vizData.getHomerseklet());
            }
        }, 100);

        inditastol_elteltido.setText(App.motorData.getInditastol_eltelt_ido());

        gazolajSzintG.setProgress(App.olajData.getGazolajSzint());
        gazolajszint_text.setText(App.olajData.getGazolajSzint() + "%");

        parameterek_feszultseg_text.setText(App.motorData.getAkkuFeszultseg() + " V");
        parameterek_olajnyomas.setText(App.olajData.getNyomas() + " bar");
        parameterek_visznyomas.setText(App.vizData.getNyomas() + " bar");

        //----------------------------------
        futasidok_datum_elso.setText(App.motorData.getFutasidok_datum_elso());
        futasidok_ido_elso.setText(App.motorData.getFutasidok_ido_elso());



        if( App.motorData.getFutasidok_eredet_elso().equals("leallitas") ){

            futasido_1_leallitas_img.setVisibility(View.VISIBLE);
            futasido_1_img.setVisibility(View.INVISIBLE);
            //futasido_1_img.setImageResource(R.drawable.ic_timer_leallitas);

        }else{
            futasido_1_leallitas_img.setVisibility(View.INVISIBLE);
            futasido_1_img.setVisibility(View.VISIBLE);
            //futasido_1_img.setImageResource(R.drawable.ic_timer_alapjarat);
        }

        futasidok_datum_masodik.setText(App.motorData.getFutasidok_datum_masodik());
        futasidok_ido_masodik.setText(App.motorData.getFutasidok_ido_masodik());
        if( App.motorData.getFutasidok_eredet_masodik().equals("leallitas") ){
            futasido_2_leallitas_img.setVisibility(View.VISIBLE);
            futasido_2_img.setVisibility(View.INVISIBLE);
            //futasido_2_img.setImageResource(R.drawable.ic_timer_leallitas);
        }else{
            futasido_2_leallitas_img.setVisibility(View.INVISIBLE);
            futasido_2_img.setVisibility(View.VISIBLE);
            //futasido_2_img.setImageResource(R.drawable.ic_timer_alapjarat);
        }

        futasidok_datum_harmadik.setText(App.motorData.getFutasidok_datum_harmadik());
        futasidok_ido_harmadik.setText(App.motorData.getFutasidok_ido_harmadik());
        if( App.motorData.getFutasidok_eredet_harmadik().equals("leallitas") ){
            futasido_3_leallitas_img.setVisibility(View.VISIBLE);
            futasido_3_img.setVisibility(View.INVISIBLE);
            //futasido_3_img.setImageResource(R.drawable.ic_timer_leallitas);
        }else{
            futasido_3_leallitas_img.setVisibility(View.INVISIBLE);
            futasido_3_img.setVisibility(View.VISIBLE);
            //futasido_3_img.setImageResource(R.drawable.ic_timer_alapjarat);
        }

        String msg = "undefined";
        if (App.vizData.isAramlas()) {
            msg = "Van";
        } else {
            msg = "Nincs";
        }
        parameterek_aramlas.setText(msg);

        if (App.vizData.isVegyszeradagolas()) {
            msg = "Van";
        } else {
            msg = "Nincs";
        }
        parameterek_vagyszer.setText(msg);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //Do your refreshing
            refreshParameterek();
            //This basically reruns this runnable in 5 seconds
            handler.postDelayed(this, 1000);
        }
    };


}
