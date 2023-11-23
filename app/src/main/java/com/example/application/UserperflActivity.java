package com.example.application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.model.bean.Data;
import com.example.application.model.dao.DataController;
import com.example.application.model.dao.UserController;
import com.example.application.utilities.FormatadorValores;

import java.util.List;

public class UserperflActivity extends AppCompatActivity {
    private EditText nomeCompleto, cargo, funcionario_id, cpf, pis, rg, dataAdmissao;
    private ImageView backButton4;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userperfl);

        initializeViews();
        token = getIntent().getStringExtra("token");
        backButton4.setOnClickListener(v -> navigateToMenuActivity());
        fill();
    }

    private void initializeViews() {
        nomeCompleto = findViewById(R.id.nomeCompleto);
        cargo = findViewById(R.id.cargo);
        funcionario_id = findViewById(R.id.funcionario_id);
        cpf = findViewById(R.id.cpf);
        pis = findViewById(R.id.pis);
        rg = findViewById(R.id.rg);
        dataAdmissao = findViewById(R.id.dataAdmissao);
        backButton4 = findViewById(R.id.backButton4);
    }

    private void navigateToMenuActivity() {
        Intent intent = new Intent(UserperflActivity.this, MenuActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
        finish();
    }

    @SuppressLint("SetTextI18n")
    private void fill() {
        DataController dataController = new DataController();
        UserController userController = new UserController();
        List<Data> lista = dataController.getAll(userController.obterFuncionarioId(token));

        if (!lista.isEmpty()) {
            Data data = lista.get(0);
            nomeCompleto.setText("Nome Completo: " + data.getNomeCompleto());
            cargo.setText("Cargo: " + data.getCargo());
            funcionario_id.setText("ID do Funcionário: " + data.getFuncionario_id());
            cpf.setText("CPF: " + FormatadorValores.formatarCPF(data.getCpf()));
            pis.setText("PIS: " + FormatadorValores.formatarPIS(data.getPis()));
            rg.setText("RG: " + FormatadorValores.formatarRG(data.getRg()));
            dataAdmissao.setText("Data de Admissão: " + FormatadorValores.formatarData(data.getDataAdmissao()));
        }
    }
}