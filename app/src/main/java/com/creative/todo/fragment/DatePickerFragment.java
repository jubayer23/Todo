package com.creative.todo.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by jubayer on 8/9/2017.
 */



/**
 * this class is for picking date from a calender
 * */
public class DatePickerFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener ondateSet;

    public DatePickerFragment() {

    }

    public void callBack(DatePickerDialog.OnDateSetListener ondate) {
        this.ondateSet = ondate;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), ondateSet, year, month, day);
    }

}