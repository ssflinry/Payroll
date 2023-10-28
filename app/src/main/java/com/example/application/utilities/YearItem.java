package com.example.application.utilities;

public class YearItem {
    private int year;

    public YearItem(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.valueOf(year);
    }
}
