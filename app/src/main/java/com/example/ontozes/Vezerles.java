package com.example.ontozes;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ontozes.data.App;
import com.github.anastr.speedviewlib.Gauge;
import com.github.anastr.speedviewlib.components.Section;

import java.util.Objects;

public class Vezerles extends Fragment {

    private Gauge fordulatszamG;
    private TextView kuplung_pozicio_data;
    private TextView kivant_pozicio_data;

    private Handler handler = new Handler();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public static Vezerles newInstance(String param1, String param2) {
        Vezerles fragment = new Vezerles();
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


    public Vezerles() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vezerles, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // app = (App) requireContext().getApplicationContext();

        fordulatszamG = getView().findViewById(R.id.fordulatszam);
        // fordulatszamG.makeSections(5, Color.argb(90,17, 232, 124), Section.Style.SQUARE);
        fordulatszamG.makeSections(5, Color.argb(100, 61, 220, 132), Section.Style.SQUARE);
        fordulatszamG.speedTo(0);

        kuplung_pozicio_data = getView().findViewById(R.id.kuplung_pozicio_data);
        kuplung_pozicio_data.setText("");

        kivant_pozicio_data = getView().findViewById(R.id.kivant_pozicio_data);
        kivant_pozicio_data.setText("");

        handler.postDelayed(runnable, 20);

    }

    @SuppressLint("SetTextI18n")
    private void refreshVezerles() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fordulatszamG.speedTo((float) App.motorData.getAktualisFordulat());
            }
        }, 100);
        kuplung_pozicio_data.setText(App.motorData.getKuplungPozicio() + "");
        kivant_pozicio_data.setText(App.motorData.getKivantPoz() + "%");
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //Do your refreshing
            refreshVezerles();
            //This basically reruns this runnable in 5 seconds
            handler.postDelayed(this, 1000);
        }
    };


}
