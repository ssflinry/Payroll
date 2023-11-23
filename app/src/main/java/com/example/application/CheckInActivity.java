package com.example.application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application.model.dao.PontoController;
import com.example.application.utilities.LiveClock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CheckInActivity extends AppCompatActivity {

    private String token;
    private int pontoStatus;
    private PontoController pontoController;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        TextView clockText = findViewById(R.id.clockText);
        ImageView backButton5 = findViewById(R.id.backButton5);
        Button entradaButton = findViewById(R.id.entradaButton);
        Button pausarButton = findViewById(R.id.pausarButton);
        Button retornoButton = findViewById(R.id.retornoButton);
        Button saidaButton = findViewById(R.id.saidaButton);
        ImageView pontoButton = findViewById(R.id.pontoButton);

        LiveClock liveClock = new LiveClock(clockText);
        liveClock.start();

        token = getIntent().getStringExtra("token");
        pontoController = new PontoController();


        pontoButton.setOnClickListener(view -> {
            Intent intent = new Intent(CheckInActivity.this, PontoListActivity.class);
            intent.putExtra("token", token);
            startActivity(intent);
            finish();
        });

        backButton5.setOnClickListener(v -> {
            Intent intent = new Intent(CheckInActivity.this, MenuActivity.class);
            intent.putExtra("token", token);
            startActivity(intent);
            finish();
        });



        entradaButton.setOnClickListener(v -> {
            pontoStatus = pontoController.obterPontoStatusId(pontoController.obterFuncionarioId(token),obterDataAtual());
            if (!pontoController.validarStatus(pontoController.obterFuncionarioId(token), obterDataAtual())) {
                realizarAcao("Entrada");
            } else {
                Toast.makeText(CheckInActivity.this, "A entrada já foi registrada.", Toast.LENGTH_SHORT).show();
            }
        });

        pausarButton.setOnClickListener(v -> {
            pontoStatus = pontoController.obterPontoStatusId(pontoController.obterFuncionarioId(token),obterDataAtual());
            if (pontoController.obterStatus(pontoStatus,"entradaStatus") && !pontoController.obterStatus(pontoStatus,"pausaStatus")) {
                realizarAcao("Pausa");
            } else if (!pontoController.obterStatus(pontoStatus,"entradaStatus")) {
                Toast.makeText(CheckInActivity.this, "Registre a entrada antes de pausar.", Toast.LENGTH_SHORT).show();
            } else if (pontoController.obterStatus(pontoStatus,"pausaStatus")) {
                Toast.makeText(CheckInActivity.this, "A pausa já foi registrada.", Toast.LENGTH_SHORT).show();
            }
        });

        retornoButton.setOnClickListener(v -> {
            pontoStatus = pontoController.obterPontoStatusId(pontoController.obterFuncionarioId(token),obterDataAtual());
            if (pontoController.obterStatus(pontoStatus,"entradaStatus") && pontoController.obterStatus(pontoStatus,"pausaStatus") && !pontoController.obterStatus(pontoStatus,"retornoStatus")) {
                realizarAcao("Retorno");
            } else if (!pontoController.obterStatus(pontoStatus,"entradaStatus")) {
                Toast.makeText(CheckInActivity.this, "Registre a entrada antes de registrar o retorno.", Toast.LENGTH_SHORT).show();
            } else if (!pontoController.obterStatus(pontoStatus,"pausaStatus")) {
                Toast.makeText(CheckInActivity.this, "Registre a pausa antes de registrar o retorno.", Toast.LENGTH_SHORT).show();
            } else if (pontoController.obterStatus(pontoStatus,"retornoStatus")) {
                Toast.makeText(CheckInActivity.this, "O retorno já foi registrado.", Toast.LENGTH_SHORT).show();
            }
        });

        saidaButton.setOnClickListener(v -> {
            pontoStatus = pontoController.obterPontoStatusId(pontoController.obterFuncionarioId(token),obterDataAtual());
            if (pontoController.obterStatus(pontoStatus,"entradaStatus") && !pontoController.obterStatus(pontoStatus,"saidaStatus")) {
                realizarAcao("Saída");
            } else if (!pontoController.obterStatus(pontoStatus,"entradaStatus")) {
                Toast.makeText(CheckInActivity.this, "Registre a entrada antes de registrar a saída.", Toast.LENGTH_SHORT).show();
            } else if (pontoController.obterStatus(pontoStatus,"saidaStatus")) {
                Toast.makeText(CheckInActivity.this, "A saída já foi registrada.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void realizarAcao(String tipoAcao) {
        String mensagem = "Deseja confirmar o registro de " + tipoAcao + "?";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação")
                .setMessage(mensagem)
                .setCancelable(false)
                .setPositiveButton("Sim", (dialog, id) -> {
                    executarAcao(tipoAcao);
                    dialog.dismiss();
                })
                .setNegativeButton("Não", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void executarAcao(String tipoAcao) {
        boolean sucesso = false;
        boolean status = false;
        switch (tipoAcao) {
            case "Entrada":
                if (pontoController.registrarStatus(pontoController.obterFuncionarioId(token))) {
                    status = true;
                    sucesso = pontoController.registrarEntrada(pontoController.obterFuncionarioId(token));
                }
                break;
            case "Pausa":
                status = pontoController.marcarStatus(pontoStatus,"pausaStatus");
                sucesso = pontoController.registrarPausa(pontoController.obterFuncionarioId(token));
                break;
            case "Retorno":
                status = pontoController.marcarStatus(pontoStatus,"retornoStatus");
                sucesso = pontoController.registrarRetorno(pontoController.obterFuncionarioId(token));
                break;
            case "Saída":
                status = pontoController.marcarStatus(pontoStatus,"saidaStatus");
                sucesso = pontoController.registrarSaida(pontoController.obterFuncionarioId(token));
                break;
        }

        if (sucesso && status) {
            registrarAcao(tipoAcao);
        } else {
            Toast.makeText(CheckInActivity.this, "ERRO: Contate o administrador do sistema!", Toast.LENGTH_SHORT).show();
        }
    }


    private void registrarAcao(String tipoAcao) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        String horaAcao = sdf.format(new Date());

        String mensagem = tipoAcao + " registrada às " + horaAcao;
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
    private String obterDataAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        return sdf.format(new Date());
    }
}