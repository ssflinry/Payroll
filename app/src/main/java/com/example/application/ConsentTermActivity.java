package com.example.application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ConsentTermActivity extends AppCompatActivity {

    public Button backButton2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent_term);

        backButton2 = findViewById(R.id.backButton2);

        backButton2.setOnClickListener(v -> {
            Intent intent = new Intent(ConsentTermActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}