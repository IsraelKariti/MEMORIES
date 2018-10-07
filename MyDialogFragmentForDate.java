package com.example.izi.memories;

import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.izi.memories.Utility.TotalDay;

import java.util.Calendar;

public class MyDialogFragmentForDate extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    ChangeToSpecificDateInterface changeToSpecificDateInterface;

    interface ChangeToSpecificDateInterface{
        public void changeToSpecificDate(int totalDay);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day_of_month = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of TimePickerDialog and return it
        return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,  this, year, month, day_of_month);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int totalDay = TotalDay.getTotalDayForSpecificDay(year, month, dayOfMonth); // month is 0-based
        changeToSpecificDateInterface.changeToSpecificDate(totalDay);
    }

    @Override
    public void onAttach(Context context) {
        changeToSpecificDateInterface = (ChangeToSpecificDateInterface) context;
        super.onAttach(context);
    }
}
