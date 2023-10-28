package com.example.application.utilities;

import android.annotation.SuppressLint;

import java.text.DateFormatSymbols;
import java.util.Locale;

public class MonthItem {
    private int month;

    public MonthItem(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }


    @SuppressLint("DefaultLocale")
    public String toString() {
        return String.format("%02d", month);
    }
    public String getMonthName() {
        DateFormatSymbols symbols = new DateFormatSymbols(new Locale("pt", "BR"));
        String[] monthNames = symbols.getMonths();
        return monthNames[month - 1];
    }
}