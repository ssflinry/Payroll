package com.example.application.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.application.utilities.MonthItem;
import java.util.List;

public class MonthAdapter extends ArrayAdapter<MonthItem> {

    public MonthAdapter(Context context, List<MonthItem> monthList) {
        super(context, 0, monthList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(getContext());
        }

        TextView textView = (TextView) convertView;
        MonthItem monthItem = getItem(position);

        if (monthItem != null) {
            textView.setText(monthItem.getMonthName());
        }

        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(getContext());
        }

        TextView textView = (TextView) convertView;
        MonthItem monthItem = getItem(position);

        if (monthItem != null) {
            textView.setText(monthItem.getMonthName());
        }

        return textView;
    }
}






