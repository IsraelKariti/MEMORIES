package com.example.izi.memories;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.izi.memories.Utility.TotalDay;

public class MyDialogFragment extends DialogFragment {

    InsertMemoryInterface insertMemoryInterface;

    interface InsertMemoryInterface{
        public void InsertMemory(String memory, int totalDay);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);;
        super.onActivityCreated(savedInstanceState);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText editText = new EditText(getContext());
        builder.setView(editText);
        builder.setMessage("New memory")
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get the memory
                        String memory = editText.getText().toString();

                        // get the totalday
                        int totalDay = TotalDay.getTotalDay();

                        insertMemoryInterface.InsertMemory(memory, totalDay);

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        return;
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        Log.i("XXXXX", "ON ATTACH - ADDD");
        insertMemoryInterface = (InsertMemoryInterface) context;
        super.onAttach(context);
    }
}
