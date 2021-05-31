package com.example.billtracker;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;

public class Summary_Activity extends MainActivity {
    public static ArrayList<String> dates =new ArrayList<>() ;
    Calendar cal = Calendar.getInstance();
    public   int  currentYear = cal.get(Calendar.YEAR);
    public int currentMonth = cal.get(Calendar.MONTH);
    ListView myList;
Button monthYear;
TextView textField;

    //go back to the top activity in the stack on back button pressed
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }




    @SuppressLint("SetTextI18n")
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_);
        monthYear = (Button) findViewById(R.id.monthYearB);
        PieChart pieChart = (PieChart) findViewById(R.id.piechart);
textField = (TextView) findViewById(R.id.textView);
        SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);



      BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.summary);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.modify:
                        startActivity(new Intent(getApplicationContext(), Modify_Activity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.summary:
                        return true;
                }
                return false;
            }

        });


dates.add(String.valueOf(currentMonth+1));
dates.add(String.valueOf(currentYear));
        monthYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
                pickerDialog.show(getSupportFragmentManager(), "MonthYearPickerDialog");
                pickerDialog.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
                        Toast.makeText(Summary_Activity.this, month + "-" + year, Toast.LENGTH_SHORT).show();
                            dates.add(String.valueOf(month));
                            dates.add(String.valueOf(year));
                    }
                });
              }
       });

        ArrayList<PieEntry> myPieChart = new ArrayList<>();
        ArrayList<Categories> allCategories = categories;


if (!dates.isEmpty()) {

    for (int i = 0; i < allCategories.size(); i++){
        double sum = sumOfBills(String.valueOf(dates.get(0)), String.valueOf(dates.get(1)), allCategories.get(i).getCategoryName(), bills);
        if (sum!=0) {
            myPieChart.add(new PieEntry((float) sum, categories.get(i).getCategoryName()));
        }
    }
}
        if (myPieChart.isEmpty()){
            pieChart.setVisibility(View.INVISIBLE);
            textField.setText("No data available for "+ String.valueOf(dates.get(0))+ "/"+ String.valueOf(dates.get(1))+ ". Try the other date or add your bills");
        }
      else {
            PieDataSet pieDataSet = new PieDataSet(myPieChart, null);
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet.setValueTextColor(Color.BLACK);
            pieDataSet.setValueTextSize(16f);
            PieData pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieChart.getDescription().setEnabled(false);
            pieChart.animate();
            pieChart.setVisibility(View.VISIBLE);
        }
        dates.clear();
    }


public double sumOfBills (String month, String year, String categoryName, Bills bills){
        ArrayList <Double> allBills = bills.queryCategories(categoryName,year,month);
        double sum = 0;
        for (int i = 0; i<allBills.size();i++){
            sum+=allBills.get(i);
        }
        return sum;
}
}