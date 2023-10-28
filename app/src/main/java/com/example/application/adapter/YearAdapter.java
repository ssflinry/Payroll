package com.example.application.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.application.utilities.YearItem;

import java.util.List;

public class YearAdapter extends ArrayAdapter<YearItem> {

    public YearAdapter(Context context, List<YearItem> yearList) {
        super(context, 0, yearList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(getContext());
        }

        TextView textView = (TextView) convertView;
        YearItem yearItem = getItem(position);

        if (yearItem != null) {
            textView.setText(yearItem.toString());
        }

        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new TextView(getContext());
        }

        TextView textView = (TextView) convertView;
        YearItem yearItem = getItem(position);

        if (yearItem != null) {
            textView.setText(yearItem.toString());
        }

        return textView;
    }
}
