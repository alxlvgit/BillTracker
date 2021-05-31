package com.example.billtracker;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

public class Categories {

private final String categoryName;
ArrayList<Double> bill;
    public Categories (String categoryName) {
        this.bill = new ArrayList<Double>();
        this.categoryName = categoryName;
    }

public void  addBill(double billAmount){
this.bill.add(billAmount);
}
    public ArrayList<Double> getBills() {
        return bill;
    }

    public String getCategoryName() {
        return categoryName;
    }

}
