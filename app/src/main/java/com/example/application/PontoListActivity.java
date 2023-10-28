package com.example.application;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.model.bean.Ponto;
import com.example.application.model.dao.PontoDAO;
import com.example.application.model.dao.UserDAO;
import com.example.application.utilities.FormatadorValores;

import java.util.ArrayList;
import java.util.List;

public class PontoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponto_list);

        ListView listViewPontos = findViewById(R.id.listViewPontos);

        String token = getIntent().getStringExtra("token");

        PontoDAO pontoDAO = new PontoDAO();
        UserDAO userDAO = new UserDAO();

        List<Ponto> listaPontos = pontoDAO.obterPontosDoFuncionario(userDAO.obterFuncionarioId(token));

        List<String> listaRegistros = new ArrayList<>();
        for (Ponto ponto : listaPontos) {
            String registro = "Tipo: " + ponto.getTipo() + ", Data/Hora: " + FormatadorValores.formatarDataHora(ponto.getDataHora());
            listaRegistros.add(registro);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaRegistros);
        listViewPontos.setAdapter(adapter);
    }



}
