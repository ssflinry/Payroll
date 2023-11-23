package com.example.application;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.utilities.EmailSender;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.editTextEmail);
        Button buttonResetPassword = findViewById(R.id.buttonResetPassword);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
                progressDialog.setMessage("Enviando e-mail...");
                progressDialog.show();

                String userEmail = editTextEmail.getText().toString();
                String confirmationCode = EmailSender.generateConfirmationCode();
                boolean emailSent = EmailSender.sendConfirmationCode(ForgotPasswordActivity.this,userEmail, confirmationCode);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if (emailSent) {
                            Intent intent = new Intent(ForgotPasswordActivity.this, ConfirmationActivity.class);
                            intent.putExtra("CONFIRMATION_CODE", confirmationCode);
                            intent.putExtra("email", userEmail);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Falha ao enviar o email de redefinição de senha.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 3000);
            }
        });
}

    }