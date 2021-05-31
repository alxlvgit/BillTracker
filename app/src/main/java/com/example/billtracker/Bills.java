package com.example.billtracker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Bills {

    ArrayList<Dates> dates;

    public Bills() {
        this.dates = new ArrayList<>();
    }

    private Dates findDate(String year, String month, String day) {
        for (int i = 0; i < this.dates.size(); i++) {
            Dates checkedDate = this.dates.get(i);
            if (checkedDate.getYear().equals(year) && checkedDate.getMonth().equals(month) && checkedDate.getDay().equals(day)) {
                return checkedDate;
            }
        }
        return null;
    }

    public ArrayList<Dates> findDatesByMonthYear (String year,String month){
        ArrayList<Dates> myDatesByMonthYear = new ArrayList<>();
        for (int i = 0; i < this.dates.size(); i++){
            Dates dates = this.dates.get(i);
            if (dates.getYear().equals(year)&& dates.getMonth().equals(month)){
                myDatesByMonthYear.add(dates);
            }
        }
        return myDatesByMonthYear;
    }


    public boolean addNewCategory(String year, String month, String day, String categoryName) {
        Dates myDate = findDate(year, month, day);
        return myDate != null && myDate.newCategory(categoryName);
    }


    public boolean addNewDate(String year, String month, String day) {
        if ((findDate(year, month, day)) == null) {
            this.dates.add(new Dates(year, month, day));
            return true;
        }
        return false;
    }



    public void addBill(double billAmount, String category, String year, String month, String  day) {
        Dates foundDate = findDate(year, month, day);
        if (foundDate != null) {
            foundDate.addBill(billAmount, category);
            // return true;
        }
        //return false;
    }


    public ArrayList<Double> queryCategories (String categoryName, String year, String month){
        ArrayList<Double> bills = new ArrayList<>();
        ArrayList<Dates> myDates = findDatesByMonthYear(year, month);
        if (myDates!=null) {
            for (int i = 0; i < myDates.size(); i++) {
                Dates date = myDates.get(i);
                if (date != null) {
                    ArrayList<Categories> monthYearCategories = date.getCategories();
                    for (int j = 0; j < monthYearCategories.size(); j++) {
                        Categories category = monthYearCategories.get(j);
                        if (category.getCategoryName().equals(categoryName)) {
                            ArrayList<Double> getBills = category.getBills();
                            bills.addAll(getBills);
                        }
                    }
                }
            }
            return  bills;
        }
        return null;
    }
}

