package com.example.billtracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.fragment.app.DialogFragment;


public class MonthYearPickerDialog extends DialogFragment {
    private static final int MAX_YEAR = 2099;
    public  static  ArrayList<String> arrLst = new ArrayList<>();
    private DatePickerDialog.OnDateSetListener listener;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }


    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final Calendar cal = Calendar.getInstance();
        View dialog = inflater.inflate(R.layout.month_year_picker_dialog, null);
         final NumberPicker monthPicker = (NumberPicker) dialog.findViewById(R.id.picker_month);
         final NumberPicker yearPicker = (NumberPicker) dialog.findViewById(R.id.picker_year);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(cal.get(Calendar.MONTH) + 1);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(2000);
        yearPicker.setMaxValue(year);
        yearPicker.setValue(year);

        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                mMonth = i1;
            }
        });

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                yYear = i1;
            }
        });

        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                mMonth = i1;
            }
        });
        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0);
                        Intent intent = new Intent(getContext(),Summary_Activity.class);
                      ((Summary_Activity)getActivity()).finish();
                        ((Summary_Activity)getActivity()).overridePendingTransition( 0, 0);
                       ((Summary_Activity)getActivity()).startActivity(intent);
                        ((Summary_Activity)getActivity()).overridePendingTransition( 0, 0);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MonthYearPickerDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }


    public  int mMonth;
    public  int yYear;

    public ArrayList<String> getMonth (){
        return arrLst;
    }

    public int getYear(){
        return yYear;
    }
}
