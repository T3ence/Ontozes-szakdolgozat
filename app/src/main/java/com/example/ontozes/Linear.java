package com.example.ontozes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ontozes.data.App;
import com.lukelorusso.verticalseekbar.VerticalSeekBar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Linear#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Linear extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView linear_kapcsolat;
    private TextView linear_ido_telepitesig;
    private TextView linear_ontozesi_mennyiseg;
    private TextView linear_nyomas;
    private TextView linear_hatralevo_meter;
    private VerticalSeekBar linearSeek;

    private TextView linear_lon;
    private TextView linear_lat;


    private Handler handler = new Handler();
    private App app;

    public Linear() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Linear.
     */
    // TODO: Rename and change types and number of parameters
    public static Linear newInstance(String param1, String param2) {
        Linear fragment = new Linear();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_linear, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            linear_kapcsolat = getView().findViewById(R.id.linear_kapcsolat);

            //TODO: még nincsen
            //linear_ido_telepitesig = getView().findViewById(R.id.linear_ido_telepitesig);
            //linear_ontozesi_mennyiseg = getView().findViewById(R.id.linear_ontozesi_mennyiseg);

            linear_nyomas = getView().findViewById(R.id.linear_nyomas);
            linear_lon = getView().findViewById(R.id.linear_lon);
            linear_lat = getView().findViewById(R.id.linear_lat);

            //TODO: grafikon
            //linear_hatralevo_meter = getView().findViewById(R.id.linear_hatralevo_meter);
            //linearSeek = getView().findViewById(R.id.linearSeek);

        }catch (Exception e){
            System.out.println("Hiba a Lineár-view kreálásánál");
        }


        handler.postDelayed(runnable, 20);
    }

    public void  refreshLinear(View view){
        linear_kapcsolat.setText( app.linearData.isKapcsolat() ? "Van" : "Nincs" );
        linear_nyomas.setText( app.linearData.getViz_nyomas() + " bar" );
        linear_ontozesi_mennyiseg.setText( app.linearData.getOntozesi_mennyiseg() + " mm");

        linear_lon.setText( app.linearData.getLon() + "" );
        linear_lat.setText( app.linearData.getLat() + "" );

    }


    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //Do your refreshing
            try {
                refreshLinear(getView());
            }catch (Exception e){
                System.out.println( e.getMessage() );
            }
            //This basically reruns this runnable in 5 seconds
            handler.postDelayed(this, 1000);
        }
    };
}