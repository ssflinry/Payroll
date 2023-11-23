package com.example.application;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.model.bean.Ponto;
import com.example.application.model.dao.PontoController;
import com.example.application.utilities.FormatadorValores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class PontoListActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponto_list);

        ListView listViewPontos = findViewById(R.id.listViewPontos);

        String token = getIntent().getStringExtra("token");

        PontoController pontoController = new PontoController();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        List<Ponto> listaPontos = pontoController.obterPontosDoFuncionario(pontoController.obterPontoStatusId(pontoController.obterFuncionarioId(token), sdf.format(new Date())));

        List<String> listaRegistros = new ArrayList<>();
        for (Ponto ponto : listaPontos) {
            String registro = "Tipo: " + ponto.getTipo() + ", Data/Hora: " + FormatadorValores.formatarDataHora(ponto.getDataHora());
            listaRegistros.add(registro);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaRegistros);
        listViewPontos.setAdapter(adapter);
    }



}