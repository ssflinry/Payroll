package com.example.application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private TextView ProfileLabel, HoleriteLabel, CheckInLabel;
    private ImageView ProfileIcon, HoleriteIcon, CheckInIcon;
    private ImageView ProfileImageView, HoleriteImageView, CheckInImageView;

    private void initializeElements(int labelId, int iconId, int imageId) {
        TextView label = findViewById(labelId);
        ImageView icon = findViewById(iconId);
        ImageView image = findViewById(imageId);

        if (labelId == R.id.ProfileLabel) {
            ProfileLabel = label;
            ProfileIcon = icon;
            ProfileImageView = image;
        } else if (labelId == R.id.HoleriteLabel) {
            HoleriteLabel = label;
            HoleriteIcon = icon;
            HoleriteImageView = image;
        } else if (labelId == R.id.CheckInLabel) {
            CheckInLabel = label;
            CheckInIcon = icon;
            CheckInImageView = image;
        }
    }

    private String token;

    @SuppressLint({"MissingInflatedId", "CutPasteId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initializeElements(R.id.ProfileLabel, R.id.ProfileIcon, R.id.ProfileImageView);
        initializeElements(R.id.HoleriteLabel, R.id.HoleriteIcon, R.id.HoleriteImageView);
        initializeElements(R.id.CheckInLabel, R.id.CheckInIcon, R.id.CheckInImageView);

        token = getIntent().getStringExtra("token");

        setupClickListeners();
    }
    public void setupClickListeners() {
        // Para Profile
        setOnClickAction(ProfileLabel, UserperflActivity.class);
        setOnClickAction(ProfileIcon, UserperflActivity.class);
        setOnClickAction(ProfileImageView, UserperflActivity.class);

        // Para Holerite
        setOnClickAction(HoleriteLabel, HoleriteActivity.class);
        setOnClickAction(HoleriteIcon, HoleriteActivity.class);
        setOnClickAction(HoleriteImageView, HoleriteActivity.class);

        // Para CheckIn
        setOnClickAction(CheckInLabel, CheckInActivity.class);
        setOnClickAction(CheckInIcon, CheckInActivity.class);
        setOnClickAction(CheckInImageView, CheckInActivity.class);
    }
    private void setOnClickAction(View view, Class<?> activityClass) {
        View.OnClickListener clickListener = v -> {
            Intent intent = new Intent(MenuActivity.this, activityClass);
            intent.putExtra("token", token);
            startActivity(intent);
        };
        view.setOnClickListener(clickListener);
    }
}