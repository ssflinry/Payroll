package com.example.application;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.model.dao.RecoveryController;

public class ConfirmationActivity extends AppCompatActivity {

    private EditText editTextConfirmationCode;
    private String generatedCode;
    private String userEmail;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        editTextConfirmationCode = findViewById(R.id.editTextConfirmationCode);
        Button buttonVerifyCode = findViewById(R.id.buttonVerifyCode);

        buttonVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(ConfirmationActivity.this);
                progressDialog.setMessage("Carregando...");
                progressDialog.show();

                String enteredCode = editTextConfirmationCode.getText().toString().trim();
                generatedCode = getIntent().getStringExtra("CONFIRMATION_CODE");
                userEmail = getIntent().getStringExtra("email");

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                        if (enteredCode.equals(generatedCode)) {
                            Toast.makeText(ConfirmationActivity.this, "Código de confirmação correto!", Toast.LENGTH_SHORT).show();
                            String token = RecoveryController.obterTokenPorEmail(userEmail);
                            RecoveryController.updateFirstAccess(token,1);
                            Intent intent = new Intent(ConfirmationActivity.this, FirstAccessActivity.class);
                            intent.putExtra("token", token);
                            intent.putExtra("msg", "Por favor, insira sua nova senha nos campos abaixo e confirme-a para concluir o processo de redefinição:");
                            startActivity(intent);
                        } else {
                            Toast.makeText(ConfirmationActivity.this, "Código de confirmação incorreto!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 3000);
            }
        });
    }
}


