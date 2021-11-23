package com.example.ontozes.communication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.ontozes.R;
import com.example.ontozes.data.App;

import org.jetbrains.annotations.NotNull;

public class ArduinoRestartDialog extends AppCompatDialogFragment {

    private ArduinoRestartDialog.ArduinoRestartDialogListener arduinoRestartDialogListener;
    private App app;

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_arduino_restart_dialog, null);


        builder.setView(view)
                .setTitle("Újraindítod az arduinot?")
                .setNegativeButton("Mégse", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Újraindítás", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arduinoRestartDialogListener.applyArduinoRestart();
                    }
                });



        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            arduinoRestartDialogListener = (ArduinoRestartDialog.ArduinoRestartDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement applyArduinoRestart()");
        }
    }

    public interface ArduinoRestartDialogListener {
        void applyArduinoRestart();
    }

}
