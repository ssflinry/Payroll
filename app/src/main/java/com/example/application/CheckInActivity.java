package com.example.application;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.application.model.dao.PontoDAO;
import com.example.application.model.dao.UserDAO;
import com.example.application.utilities.LiveClock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CheckInActivity extends AppCompatActivity {

    private boolean pausado = false;
    private String token;
    private boolean entradaRegistrada = false;
    private boolean pausaRegistrada = false;
    private boolean retornoRegistrado = false;
    private boolean saidaRegistrada = false;
    private String dataRegistro = "";

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

        pontoButton.setOnClickListener(view -> {
            Intent intent = new Intent(CheckInActivity.this, PontoListActivity.class);
            intent.putExtra("token", token);
            startActivity(intent);
            finish();
        });

        entradaButton.setOnClickListener(v -> {
            if (!entradaRegistrada && verificarUmaBaterPorDia()) {
                realizarAcao("Entrada");
                entradaRegistrada = true;
                dataRegistro = obterDataAtual();
            } else if (entradaRegistrada) {
                Toast.makeText(CheckInActivity.this, "A entrada já foi registrada.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CheckInActivity.this, "Você já registrou uma vez hoje.", Toast.LENGTH_SHORT).show();
            }
        });

        pausarButton.setOnClickListener(v -> {
            if (entradaRegistrada && !pausaRegistrada) {
                realizarAcao("Pausa");
                pausaRegistrada = true;
            } else if (!entradaRegistrada) {
                Toast.makeText(CheckInActivity.this, "Registre a entrada antes de pausar.", Toast.LENGTH_SHORT).show();
            } else if (pausaRegistrada) {
                Toast.makeText(CheckInActivity.this, "A pausa já foi registrada.", Toast.LENGTH_SHORT).show();
            }
        });

        retornoButton.setOnClickListener(v -> {
            if (entradaRegistrada && pausaRegistrada && !retornoRegistrado) {
                realizarAcao("Retorno");
                retornoRegistrado = true;
            } else if (!entradaRegistrada) {
                Toast.makeText(CheckInActivity.this, "Registre a entrada antes de registrar o retorno.", Toast.LENGTH_SHORT).show();
            } else if (!pausaRegistrada) {
                Toast.makeText(CheckInActivity.this, "Registre a pausa antes de registrar o retorno.", Toast.LENGTH_SHORT).show();
            } else if (retornoRegistrado) {
                Toast.makeText(CheckInActivity.this, "O retorno já foi registrado.", Toast.LENGTH_SHORT).show();
            }
        });

        saidaButton.setOnClickListener(v -> {
            if (entradaRegistrada && !saidaRegistrada) {
                realizarAcao("Saída");
                saidaRegistrada = true;
            } else if (!entradaRegistrada) {
                Toast.makeText(CheckInActivity.this, "Registre a entrada antes de registrar a saída.", Toast.LENGTH_SHORT).show();
            } else if (saidaRegistrada) {
                Toast.makeText(CheckInActivity.this, "A saída já foi registrada.", Toast.LENGTH_SHORT).show();
            }
        });


        backButton5.setOnClickListener(v -> {
            Intent intent = new Intent(CheckInActivity.this, MenuActivity.class);
            intent.putExtra("token", token);
            startActivity(intent);
            finish();
        });

    }

    private boolean verificarUmaBaterPorDia() {
        String dataAtual = obterDataAtual();
        return !dataAtual.equals(dataRegistro);
    }
    private String obterDataAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        return sdf.format(new Date());
    }
    private void realizarAcao(String tipoAcao) {
        String mensagem = "Deseja confirmar o registro de " + tipoAcao + "?";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação")
                .setMessage(mensagem)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        PontoDAO pontoDAO = new PontoDAO();
                        UserDAO userDAO = new UserDAO();
                        boolean sucesso = false;

                        switch (tipoAcao) {
                            case "Entrada":
                                sucesso = pontoDAO.registrarEntrada(userDAO.obterFuncionarioId(token));
                                break;
                            case "Pausa":
                                if (!pausado) {
                                    sucesso = pontoDAO.registrarPausa(userDAO.obterFuncionarioId(token));
                                    if (sucesso) {
                                        pausado = true;
                                    }
                                } else {
                                    Toast.makeText(CheckInActivity.this, "Não é possível registrar a pausa durante a pausa atual.", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case "Retorno":
                                if (pausado) {
                                    sucesso = pontoDAO.registrarRetorno(userDAO.obterFuncionarioId(token));
                                    if (sucesso) {
                                        pausado = false;
                                    }
                                } else {
                                    Toast.makeText(CheckInActivity.this, "O sistema não está em pausa para registrar o retorno.", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            case "Saída":
                                sucesso = pontoDAO.registrarSaida(userDAO.obterFuncionarioId(token));
                                break;
                        }

                        if (sucesso) {
                            registrarAcao(tipoAcao);
                        }

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void registrarAcao(String tipoAcao) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        String horaAcao = sdf.format(new Date());

        String mensagem = tipoAcao + " registrada às " + horaAcao;
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

}