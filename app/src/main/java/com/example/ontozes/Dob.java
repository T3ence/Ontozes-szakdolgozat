package com.example.ontozes;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ontozes.communication.ArduinoRestartDialog;
import com.example.ontozes.data.App;
import com.lukelorusso.verticalseekbar.VerticalSeekBar;

public class Dob extends Fragment {

    private TextView dob_uzemmod;
    private TextView dob_helyzet;
    private TextView dob_nyomas;
    private TextView dob_sebesseg;
    private VerticalSeekBar verticalSeekBar;

    private final Handler handler = new Handler();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private String elozoUzemmod = "valami";

    private ImageView uzemmod_img;
    private ImageView uzemmod_ontozes_img;

    public static Dob newInstance(String param1, String param2) {
        Dob fragment = new Dob();
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



    public Dob() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dob, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // app = (App) requireContext().getApplicationContext();

        dob_uzemmod = requireView().findViewById(R.id.dob_uzemmod);
        dob_helyzet = getView().findViewById(R.id.dob_helyzet);
        dob_nyomas = getView().findViewById(R.id.dob_nyomas);
        dob_sebesseg = getView().findViewById(R.id.dob_sebesseg);
        verticalSeekBar = getView().findViewById(R.id.kontolSeek);
        uzemmod_img = getView().findViewById(R.id.uzemmod_img);
        uzemmod_ontozes_img = getView().findViewById(R.id.uzemmod_ontozes_img);

        verticalSeekBar.setProgress(0);
        verticalSeekBar.setMaxValue(360);
        verticalSeekBar.setClickToSetProgress(false);
        verticalSeekBar.setUseThumbToSetProgress(false);

        dob_uzemmod.setText("");
        dob_helyzet.setText(0 + " m");
        dob_nyomas.setText(0 + " bar");
        dob_sebesseg.setText(0 + " m/óra");

        ImageView bauer_logo = getView().findViewById(R.id.bauer_logo);
        bauer_logo.setOnLongClickListener(view1 -> {
            ArduinoRestartDialog restartDialog = new ArduinoRestartDialog();
            restartDialog.show(getFragmentManager(), "Arduino Restart");
            return false;
        });


        handler.postDelayed(runnable, 20);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    private void refreshDob(View view) {

        String uzemmod = App.dobData.getUzemmod() + "";
        if(uzemmod.equals("")){
            uzemmod = "Kikapcsolva";
        }
        dob_uzemmod.setText(uzemmod);
        dob_helyzet.setText(App.dobData.getHelyzet() + " m");
        dob_nyomas.setText(App.dobData.getNyomas() + " bar");
        dob_sebesseg.setText(App.dobData.getSebesseg() + " m/óra");

        if(!elozoUzemmod.equals( App.dobData.getUzemmod() ) ){

            if (App.dobData.getUzemmod().equals("Öntözés")) {

            /* TODO: átnézni
            TypedValue typedValue2 = new TypedValue();
            Resources.Theme theme2 = view.getContext().getTheme();
            theme2.resolveAttribute(R.attr.ontozes_logo, typedValue2, true);
            @DrawableRes int drawableres = typedValue2.data;
             */

                //uzemmod_img.setBackgroundResource(R.drawable.ic_ontozes);
                uzemmod_img.setVisibility(View.INVISIBLE);
                uzemmod_ontozes_img.setVisibility(View.VISIBLE);
                verticalSeekBar.setThumbPlaceholderDrawable(getActivity().getDrawable(R.drawable.konzol_ontoz_kep));

                /*víz - fix színek*/
                verticalSeekBar.setBarBackgroundStartColor(Color.parseColor("#FF4EB4FF"));
                verticalSeekBar.setBarBackgroundEndColor(Color.parseColor("#FFD6F0FF"));

                /*Szárazság*/
                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = view.getContext().getTheme();
                theme.resolveAttribute(R.attr.BarProgressStartColor_ontozes, typedValue, true);
                @ColorInt int color = typedValue.data;

                verticalSeekBar.setBarProgressStartColor(color);

                theme.resolveAttribute(R.attr.BarProgressEndColor_ontozes, typedValue, true);
                color = typedValue.data;
                verticalSeekBar.setBarProgressEndColor(color);

                elozoUzemmod = "Öntözes";

            /*
            verticalSeekBar.setBarBackgroundStartColor(Color.parseColor("#FF4EB4FF"));
            verticalSeekBar.setBarBackgroundEndColor(Color.parseColor("#FFD6F0FF"));
            verticalSeekBar.setBarProgressStartColor(Color.parseColor("#FFF0F7CF"));
            verticalSeekBar.setBarProgressEndColor(Color.parseColor("#8A3DDB85"));
             */

            } else {
                //uzemmod_img.setBackgroundResource(R.drawable.ic_telepites);
                uzemmod_img.setVisibility(View.VISIBLE);
                uzemmod_ontozes_img.setVisibility(View.INVISIBLE);

                verticalSeekBar.setThumbPlaceholderDrawable(getActivity().getDrawable(R.drawable.konzol_telepites_kep));

                TypedValue typedValue = new TypedValue();
                Resources.Theme theme = view.getContext().getTheme();
                theme.resolveAttribute(R.attr.BarBackgroundStartColor_telepites, typedValue, true);
                @ColorInt int color = typedValue.data;
                @ColorInt int color2 = color;

                verticalSeekBar.setBarBackgroundStartColor(color);

                theme.resolveAttribute(R.attr.BarBackgroundEndColor_telepites, typedValue, true);
                color = typedValue.data;

                verticalSeekBar.setBarBackgroundEndColor(color);

                theme.resolveAttribute(R.attr.BarProgressStartColor_telepites, typedValue, true);
                color = typedValue.data;

                verticalSeekBar.setBarProgressStartColor(color);

                theme.resolveAttribute(R.attr.BarProgressEndColor_telepites, typedValue, true);
                color = typedValue.data;

                verticalSeekBar.setBarProgressEndColor(color2);

                elozoUzemmod = "Telepítés";

            /*
            verticalSeekBar.setBarBackgroundStartColor(Color.parseColor("#8A3DDB85"));
            verticalSeekBar.setBarBackgroundEndColor(Color.parseColor("#FFF0F7CF"));
            verticalSeekBar.setBarProgressStartColor(Color.parseColor("#FFF0F7CF"));
            verticalSeekBar.setBarProgressEndColor(Color.parseColor("#8A3DDB85"));
            */
            }
        }

        verticalSeekBar.setProgress((int) Math.round(App.dobData.getHelyzet()));
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                refreshDob(getView());
            }catch (Exception e){
                System.out.println( e.getMessage() );
            }
            handler.postDelayed(this, 1000);
        }
    };


}
