package com.example.billtracker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {



    private static final String TAG =null;
    private TextView chooseTxt;
    private EditText billInput;
    private Button billButton;
    private Button monthYearButton;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public  static ArrayList<String> billsList = new ArrayList<>();
    public ArrayList<Categories> categories = new ArrayList<>();
    public static Bills bills = new Bills();

    public static String newCat;
    public static String myYear;
    public static String myMonth;
    public static String myDay;



    //go back to the top activity in the stack on back button pressed
    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill_);
        billInput = (EditText) findViewById(R.id.billInput);
        billButton = (Button) findViewById(R.id.button);
        chooseTxt = (TextView) findViewById(R.id.choose_txt);
        monthYearButton = (Button) findViewById(R.id.monthButton);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        return true;

                    case R.id.modify:
                        startActivity(new Intent(getApplicationContext(), Modify_Activity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.summary:
                        if (billsList.isEmpty()) {
                            Log.d(TAG, "list is empty");
                            startActivity(new Intent(getApplicationContext(), Summary_Activity.class));
                            overridePendingTransition(0, 0);
                            return true;
                        }
                        else {
                            Log.d(TAG, "list is not empty");
                            Intent intent = new Intent(MainActivity.this, Summary_Activity.class);
                            intent.putExtra("Extra", billsList);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            return true;
                        }
                }
                return false;
            }
        });

// write bills object to shared preferences if doesn't exist
        Gson gson = new Gson();
        String json = mPrefs.getString("MyBills", "");
        Bills bills1 = gson.fromJson(json, Bills.class);
        if (bills1!= null){
            bills = bills1;
        }



        // Date Selector///
        monthYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                myYear = String.valueOf(year);
                myMonth = String.valueOf(month);
                myDay = String.valueOf(day);
                final String date = month + "/" + day + "/" + year;

            }
        };



        //Add categories and list them in spinner widget
        final List<String> categoriesList= new ArrayList<String>();
        categoriesList.add("Choose Category:");
        addCategory(categories,"Food");
        addCategory(categories,"Transportation");
        addCategory(categories,"Others");
        for (int i = 0; i < categories.size();i++){
            Categories category = categories.get(i);
            categoriesList.add(category.getCategoryName());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, categoriesList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                newCat = spinner.getSelectedItem().toString();
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



// 'add bill' button listener
        billButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override

            public void onClick(View view) {
                if (!(billInput == null) && billInput.length() != 0) {
                    final String date = myMonth + "/" + myDay + "/" + myYear;
                    double setBill = Double.parseDouble(billInput.getText().toString());

                    if (setBill != 0 && newCat != null && !newCat.equals("Choose Category:") && myDay != null && myMonth != null && myYear != null) {

                        if (bills.addNewDate(myYear,myMonth,myDay)){
                            Toast.makeText(MainActivity.this,"New date added: "+date , Toast.LENGTH_SHORT).show();
                        }

                        if (!newCat.equals("Choose Category:") &&  bills.addNewCategory(myYear, myMonth, myDay, newCat)) {
                            Toast.makeText(MainActivity.this,newCat+" category added", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            Toast.makeText(MainActivity.this,newCat+" category selected", Toast.LENGTH_SHORT).show();
                        }

                        bills.addBill(setBill, newCat, myYear, myMonth, myDay);


                        //rewrite bill object to shared preferences after new bill added
                        SharedPreferences.Editor prefsEditor = mPrefs.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(bills);
                        prefsEditor.putString("MyBills", json);
                        prefsEditor.commit();


                        Toast.makeText(MainActivity.this, "Bill of $" + setBill + " was added" + " to " + newCat + " category. " + "Date: " + myMonth + "/" + myDay + "/" + myYear, Toast.LENGTH_LONG).show();

                        billInput.getText().clear();
                        spinner.setSelection(0);

                    }

                    if ((myDay == null || myMonth == null || myYear == null)) {
                        Toast.makeText(MainActivity.this, "Date is not selected. Please, select date", Toast.LENGTH_SHORT).show();
                    } else if (newCat == null || newCat.equals("Choose Category:")) {
                        Toast.makeText(MainActivity.this, "Category is not selected. Please, select category", Toast.LENGTH_SHORT).show();
                    } else if (setBill == 0) {
                        Toast.makeText(MainActivity.this, "Bill can't equal $0. Please, enter a valid value", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "Bill field is empty", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }



    public void addCategory(ArrayList <Categories> categories,String categoryName) {
        if (categoryName != null) {
            Categories myCategories = new Categories(categoryName);
            categories.add(myCategories);
        }
    }


    public void showDateDialog(){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(
                MainActivity.this,
                // android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}