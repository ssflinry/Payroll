package com.example.application.model.dao;

import android.util.Log;

import com.example.application.model.bean.Ponto;
import com.example.application.model.bean.Status;
import com.example.application.model.connection.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class PontoController {

    public int obterFuncionarioId(String token) {
        int funcionarioId = -1;

        try {
            Connection conn = Conexao.conectar();
            if (conn != null) {
                String query = "SELECT funcionario_id FROM usuarios WHERE token = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, token);

                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    funcionarioId = rs.getInt("funcionario_id");
                }
                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
        }

        return funcionarioId;
    }

    public int obterPontoStatusId(int funcionario_id, String data) {
        int pontoStatusId = -1;

        try {
            Connection conn = Conexao.conectar();
            if (conn != null) {
                String query = "SELECT id FROM ponto_status WHERE funcionario_id = ? AND CONVERT(DATE, dataHora) = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, funcionario_id);
                preparedStatement.setString(2, data);

                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    pontoStatusId = rs.getInt("id");
                }
                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
        }

        return pontoStatusId;
    }

    public boolean registrarStatus(int funcionarioId) {
        Status status = new Status();
        status.setFuncionario_id(funcionarioId);
        status.setDataHora(new Date());
        return inserirStatusInitial(status);
    }
    public boolean registrarEntrada(int funcionarioId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        Status status = new Status();
        status.setFuncionario_id(funcionarioId);

        Ponto ponto = new Ponto();
        ponto.setDataHora(new Date());
        ponto.setTipo("Entrada");
        ponto.setPontoStatus_id(obterPontoStatusId(status.getFuncionario_id(),sdf.format(ponto.getDataHora())));

        return atualizarEntrada(ponto);
    }

    public boolean registrarPausa(int funcionarioId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        Status status = new Status();
        status.setFuncionario_id(funcionarioId);

        Ponto ponto = new Ponto();
        ponto.setDataHora(new Date());
        ponto.setTipo("Pausa");
        ponto.setPontoStatus_id(obterPontoStatusId(status.getFuncionario_id(),sdf.format(ponto.getDataHora())));

        return inserirPonto(ponto);
    }

    public boolean registrarRetorno(int funcionarioId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        Status status = new Status();
        status.setFuncionario_id(funcionarioId);

        Ponto ponto = new Ponto();
        ponto.setDataHora(new Date());
        ponto.setTipo("Retorno");
        ponto.setPontoStatus_id(obterPontoStatusId(status.getFuncionario_id(),sdf.format(ponto.getDataHora())));

        return inserirPonto(ponto);
    }

    public boolean registrarSaida(int funcionarioId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        Status status = new Status();
        status.setFuncionario_id(funcionarioId);

        Ponto ponto = new Ponto();
        ponto.setDataHora(new Date());
        ponto.setTipo("Saída");
        ponto.setPontoStatus_id(obterPontoStatusId(status.getFuncionario_id(),sdf.format(ponto.getDataHora())));

        return inserirPonto(ponto);
    }
    public boolean inserirPonto(Ponto ponto) {
        try {
            Connection conn = Conexao.conectar();
            if (conn != null) {
                String query = "INSERT INTO bate_ponto (pontoStatus_id, dataHora, tipo) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);

                TimeZone saoPauloTimeZone = TimeZone.getTimeZone("America/Sao_Paulo");
                long timeInSaoPaulo = ponto.getDataHora().getTime() + saoPauloTimeZone.getOffset(ponto.getDataHora().getTime());
                Timestamp timestamp = new Timestamp(timeInSaoPaulo);

                preparedStatement.setInt(1, ponto.getPontoStatus_id());
                preparedStatement.setTimestamp(2, timestamp);
                preparedStatement.setString(3, ponto.getTipo());

                int rowsAffected = preparedStatement.executeUpdate();
                conn.close();

                return rowsAffected > 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
        }
        return false;
    }

    public boolean atualizarEntrada(Ponto ponto) {
        try {
            Connection conn = Conexao.conectar();
            if (conn != null) {
                String query = "UPDATE bate_ponto SET dataHora = ?, tipo = ? WHERE pontoStatus_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);

                TimeZone saoPauloTimeZone = TimeZone.getTimeZone("America/Sao_Paulo");
                long timeInSaoPaulo = ponto.getDataHora().getTime() + saoPauloTimeZone.getOffset(ponto.getDataHora().getTime());
                Timestamp timestamp = new Timestamp(timeInSaoPaulo);

                preparedStatement.setTimestamp(1, timestamp);
                preparedStatement.setString(2, ponto.getTipo());
                preparedStatement.setInt(3, ponto.getPontoStatus_id());

                int rowsAffected = preparedStatement.executeUpdate();
                conn.close();

                return rowsAffected > 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
        }
        return false;
    }

    public boolean marcarStatus(int id, String campo) {
        try {
            Connection conn = Conexao.conectar();
            if (conn != null) {
                String query = "UPDATE ponto_status SET " + campo + " = 1 WHERE id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();
                conn.close();

                return rowsAffected > 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
        }
        return false;
    }


    public boolean obterStatus(int id, String campo) {
        boolean status = false;

        try {
            Connection conn = Conexao.conectar();
            if (conn != null) {
                String query = "SELECT " + campo + " FROM ponto_status WHERE id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, id);

                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    status = rs.getBoolean(campo);
                }

                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
        }
        return status;
    }


    public boolean inserirStatusInitial(Status status) {
        try {
            Connection conn = Conexao.conectar();
            if (conn != null) {
                String query = "INSERT INTO ponto_status (funcionario_id, entradaStatus, dataHora) VALUES (?,1,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);

                TimeZone saoPauloTimeZone = TimeZone.getTimeZone("America/Sao_Paulo");
                long timeInSaoPaulo = status.getDataHora().getTime() + saoPauloTimeZone.getOffset(status.getDataHora().getTime());
                Timestamp timestamp = new Timestamp(timeInSaoPaulo);

                preparedStatement.setInt(1, status.getFuncionario_id());
                preparedStatement.setTimestamp(2, timestamp);

                int rowsAffected = preparedStatement.executeUpdate();
                conn.close();

                return rowsAffected > 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
        }
        return false;
    }

    public boolean validarStatus(int funcionarioId, String data) {
        try {
            Connection conn = Conexao.conectar();
            if (conn != null) {
                String query = "SELECT COUNT(*) AS total_registros FROM ponto_status WHERE funcionario_id = ? AND CONVERT(DATE, dataHora) = ?";

                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, funcionarioId);
                preparedStatement.setString(2, data);

                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    int totalRegistros = rs.getInt("total_registros");
                    conn.close();
                    return totalRegistros > 0;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
        }
        return false;
    }


    public List<Ponto> obterPontosDoFuncionario(int pontoStatusId) {
        List<Ponto> pontos = new ArrayList<>();
        try {
            Connection conn = Conexao.conectar();
            if (conn != null) {
                String query = "SELECT * FROM bate_ponto WHERE pontoStatus_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, pontoStatusId);


                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Ponto ponto = new Ponto();
                    ponto.setId(rs.getInt("id"));
                    ponto.setPontoStatus_id(rs.getInt("pontoStatus_id"));
                    ponto.setDataHora(rs.getTimestamp("dataHora"));
                    ponto.setTipo(rs.getString("tipo"));
                    pontos.add(ponto);
                }

                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("Error: ", Objects.requireNonNull(e.getMessage()));
        }
        return pontos;
    }
}