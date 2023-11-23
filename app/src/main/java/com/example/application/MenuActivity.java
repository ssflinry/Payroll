package com.example.application;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
        ImageView menuButton = findViewById(R.id.menuButton);

        token = getIntent().getStringExtra("token");

        menuButton.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(this, v);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.option1) {
                    showProgressDialog();
                    Intent intent = new Intent(MenuActivity.this, FirstAccessActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("back", true);
                    intent.putExtra("msg", "Digite a nova senha e a confirme. Lembre-se de criar uma senha forte, contendo uma combinação de letras, números e caracteres especiais para manter sua conta segura.");
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.option2) {
                    showProgressDialog();
                    Intent intent = new Intent(MenuActivity.this, HelpActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.option3) {
                    showProgressDialog();
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else {
                    return false;
                }
            });


            popupMenu.show();
        });

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
                final ProgressDialog progressDialog = new ProgressDialog(MenuActivity.this);
            progressDialog.setMessage("Carregando...");
            progressDialog.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    Intent intent = new Intent(MenuActivity.this, activityClass);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }
            }, 2000);
        };
        view.setOnClickListener(clickListener);
    }

    private void showProgressDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(MenuActivity.this);
        progressDialog.setMessage("Carregando...");
        progressDialog.show();

        new Handler().postDelayed(progressDialog::dismiss, 1000); // 1000 milliseconds (1 second)
    }
}