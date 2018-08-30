package com.ecru.infographic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

/**
 * Created by mustarohman on 08/12/2015.
 */
public class AboutDialog extends DialogFragment {
    public AboutDialog() {

    }

    /**
     * Displays about message
     * @param savedInstanceState
     * @return dialog stating source used.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_about, null))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AboutDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
