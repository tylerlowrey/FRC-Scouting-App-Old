package com.tylerlowrey.frcscoutingapp;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ErrorDialogFragment extends DialogFragment
{
    private String errorMessage;

    /**
     * Creates a new object that represents an Error Dialog
     *
     * @param message The error message that will be displayed in the dialog box
     */
    public ErrorDialogFragment(String message)
    {
        this.errorMessage = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.error_dialog_title);
        builder.setMessage(errorMessage);
        builder.setPositiveButton(R.string.ok_button_text, null);
        return builder.create();
    }
}
