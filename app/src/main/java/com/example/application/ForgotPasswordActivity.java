package com.example.application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.utilities.EmailSender;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private Button buttonResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.editTextEmail);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = editTextEmail.getText().toString();

                boolean emailSent = EmailSender.sendEmail(userEmail, "Redefinição de Senha", "Aqui está o link para redefinir sua senha.");

                if (emailSent) {
                    Toast.makeText(ForgotPasswordActivity.this, "E-mail de redefinição de senha enviado com sucesso.", Toast.LENGTH_SHORT).show();
                } else {
                    // Falha no envio do email, exiba uma mensagem de erro
                    Toast.makeText(ForgotPasswordActivity.this, "Falha ao enviar o e-mail de redefinição de senha.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
