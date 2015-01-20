package com.iribar.carlos.maldonadotiempo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Carlos Iribar on 15/01/2015.
 */
public class AlertDialogaFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.title_dialog))
                .setMessage("Hubo un error. Intenta nuevamente")
                .setPositiveButton("OK",null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}

