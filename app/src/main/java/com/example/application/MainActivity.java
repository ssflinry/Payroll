package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.model.bean.User;
import com.example.application.model.dao.UserDAO;

public class MainActivity extends AppCompatActivity {

    private EditText editTextEmailOrCPF;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        editTextEmailOrCPF = findViewById(R.id.editTextEmailOrCPF);
        editTextPassword = findViewById(R.id.editTextPassword);
        TextView privacyPolicy = findViewById(R.id.privacyPolicy);
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        TextView consentTerm = findViewById(R.id.consentTerm);

        loginButton.setOnClickListener(v -> {
            String username = editTextEmailOrCPF.getText().toString();
            String password = editTextPassword.getText().toString();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                showToast("Insira o Email ou CPF e a senha");
                return;
            }

            User user = new UserDAO().obterUser(username, password);
            if (user != null) {
                if (user.getFirst_access() == 1) {
                    Intent intent = new Intent(MainActivity.this, FirstAccessActivity.class);
                    intent.putExtra("token", user.getToken());
                    startActivity(intent);
                    finish();
                } else {
                    showToast("Logado com sucesso");
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    intent.putExtra("token", user.getToken());
                    startActivity(intent);
                    finish();
                }
            } else {
                showToast("Usuário e/ou Senha Inválida!");
                clearInputs();
            }
        });

        setClickAction(privacyPolicy, PrivacyPolicyActivity.class);
        setClickAction(consentTerm, ConsentTermActivity.class);
        setClickAction(forgotPassword, ForgotPasswordActivity.class);
    }

    private void setClickAction(TextView textView, Class<?> activityClass) {
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, activityClass);
            startActivity(intent);
            finish();
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void clearInputs() {
        editTextEmailOrCPF.setText("");
        editTextPassword.setText("");
        editTextEmailOrCPF.requestFocus();
    }
}
