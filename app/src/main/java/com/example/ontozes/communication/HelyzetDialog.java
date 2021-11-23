package com.example.ontozes.communication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.ontozes.R;

import org.jetbrains.annotations.NotNull;

public class HelyzetDialog extends AppCompatDialogFragment {

    private EditText remote_Helyzet;
    private HelyzetDialogListener helyzetDialog;

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_helyzet_dialog, null);

        builder.setView(view)
                .setTitle("Helyzet megadása")
                .setNegativeButton("Mégse", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Küldés", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String rHelyzetString = remote_Helyzet.getText().toString();
                        helyzetDialog.applyHelyzet(rHelyzetString);
                    }
                })
        ;


        remote_Helyzet = view.findViewById(R.id.remote_Helyzet);


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            helyzetDialog = (HelyzetDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement applyHelyzet()");
        }
    }

    public interface HelyzetDialogListener {
        void applyHelyzet(String rHelyzetString);
    }

}
