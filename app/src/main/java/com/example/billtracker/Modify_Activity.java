package com.example.billtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Modify_Activity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.modify);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.modify:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.summary:
//                        if (billsList.isEmpty()){
                        startActivity(new Intent(getApplicationContext(), Summary_Activity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    // }
//                        else  {
//                            Intent intent = new Intent(MainActivity.this, Summary_Activity.class);
//                            intent.putExtra("Extra", billsList);
//                            startActivity(intent);
//                            overridePendingTransition(0, 0);
//                            return  true;
//                        }
                }
                return false;
            }
        });
    }
}