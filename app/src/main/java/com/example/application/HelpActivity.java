package com.example.application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {
    private String token;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView helpText = findViewById(R.id.helpText);
        ImageView backButtonHelp = findViewById(R.id.backButtonHelp);

        token = getIntent().getStringExtra("token");

        String helpContent = "Bem-vindo à seção de Ajuda!\n\n" +
                "1. Tela de Login: Esta tela permite que os usuários façam login com seu e-mail ou CPF e senha.\n\n" +
                "2. Tela do Menu: Após o login, os usuários acessam esta tela. Aqui podem ver o perfil, holerite e fazer check-ins.\n\n" +
                "3. Tela de Perfil: Mostra informações detalhadas do usuário, como nome, cargo, etc.\n\n" +
                "4. Tela de Holerite: Exibe informações sobre o holerite do usuário, como adiantamento, descontos, etc.\n\n" +
                "5. Tela de Check-In: Permite que o usuário registre várias ações, como entrada, pausa, retorno e saída.\n\n" +
                "6. Tela de Registro de Ponto: Apresenta uma lista de todos os registros de check-in realizados pelo usuário.";

        helpText.setText(helpContent);

        backButtonHelp.setOnClickListener(v -> {
            Intent intent = new Intent(HelpActivity.this, MenuActivity.class);
            intent.putExtra("token", token);
            startActivity(intent);
            finish();
        });
    }
}
