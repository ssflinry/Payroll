package com.example.application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PrivacyPolicyActivity extends AppCompatActivity {

    public Button backButton1;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        backButton1 = findViewById(R.id.backButton1);

        backButton1.setOnClickListener(v -> {
            Intent intent = new Intent(PrivacyPolicyActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}