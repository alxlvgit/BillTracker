package com.example.billtracker;

import java.util.ArrayList;

public class Dates {
    public String year;
    public String day;
    ArrayList<Categories> categories;
    public String month;

    public Dates(String year, String month, String day ) {
        this.month = month;
        this.year = year;
        this.day = day;
        this.categories = new ArrayList<Categories>();
    }

    public ArrayList<Categories> getCategories() {
        return categories;
    }

    public String getYear() {
        return year;
    }

    public String getDay() {
        return day;
    }

    public String  getMonth() {
        return month;
    }



    public boolean newCategory(String categoryName) {
        if(findCategory(categoryName) == null) {
            this.categories.add(new Categories(categoryName));
            return true;
        }
        return false;
    }

    public void addBill(double billAmount, String category) {
        Categories foundCategory = findCategory(category);
        if (foundCategory != null) {
            foundCategory.addBill(billAmount);
           // return true;
        }
        //return false;
    }

    public Categories findCategory(String category) {
        for (int i = 0; i < this.categories.size(); i++) {
            Categories checkedCategory = this.categories.get(i);
            if (checkedCategory.getCategoryName().equals(category)) {
                return checkedCategory;
            }
        }
        return null;
    }

}
