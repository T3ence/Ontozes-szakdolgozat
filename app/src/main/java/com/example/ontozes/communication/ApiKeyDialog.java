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
import com.example.ontozes.data.App;

import org.jetbrains.annotations.NotNull;

public class ApiKeyDialog extends AppCompatDialogFragment {

    private EditText apiKely_in;
    private ApiKeyDialog.ApiKeyDialogListener apiKeyDialogListener;
    private App app;

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_apikey_dialog, null);


        builder.setView(view)
                .setTitle("Api Kulcs megadása")
                .setNegativeButton("Mégse", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Rendben", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String apiKey = apiKely_in.getText().toString();
                        apiKeyDialogListener.applyApiKey(apiKey);
                    }
                });

        apiKely_in = view.findViewById(R.id.apiKely_in);
        apiKely_in.setHint(App.api_KEY);


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            apiKeyDialogListener = (ApiKeyDialog.ApiKeyDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement applyApiKey()");
        }
    }

    public interface ApiKeyDialogListener {
        void applyApiKey(String apiKey);
    }

}