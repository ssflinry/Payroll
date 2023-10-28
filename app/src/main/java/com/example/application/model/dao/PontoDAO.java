package com.example.application.model.dao;

import android.util.Log;

import com.example.application.model.connection.Conexao;
import com.example.application.model.bean.Ponto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PontoDAO {
    public boolean registrarEntrada(int funcionarioId) {
        Ponto ponto = new Ponto();
        ponto.setFuncionario_id(funcionarioId);
        ponto.setDataHora(new Date());
        ponto.setTipo("Entrada");

        return inserirPonto(ponto);
    }

    public boolean registrarPausa(int funcionarioId) {
        Ponto ponto = new Ponto();
        ponto.setFuncionario_id(funcionarioId);
        ponto.setDataHora(new Date());
        ponto.setTipo("Pausa");

        return inserirPonto(ponto);
    }

    public boolean registrarRetorno(int funcionarioId) {
        Ponto ponto = new Ponto();
        ponto.setFuncionario_id(funcionarioId);
        ponto.setDataHora(new Date());
        ponto.setTipo("Retorno");

        return inserirPonto(ponto);
    }

    public boolean registrarSaida(int funcionarioId) {
        Ponto ponto = new Ponto();
        ponto.setFuncionario_id(funcionarioId);
        ponto.setDataHora(new Date());
        ponto.setTipo("Saída");

        return inserirPonto(ponto);
    }
    public boolean inserirPonto(Ponto ponto) {
        try {
            Connection conn = Conexao.conectar();
            if (conn != null) {
                String query = "INSERT INTO bate_ponto (funcionario_id, dataHora, tipo) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);

                preparedStatement.setInt(1, ponto.getFuncionario_id());
                preparedStatement.setTimestamp(2, new java.sql.Timestamp(ponto.getDataHora().getTime()));
                preparedStatement.setString(3, ponto.getTipo());

                int rowsAffected = preparedStatement.executeUpdate();
                conn.close();

                return rowsAffected > 0;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("Error: ", e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error: ", throwables.getMessage());
        }
        return false;
    }
    public List<Ponto> obterPontosDoFuncionario(int funcionarioId) {
        List<Ponto> pontos = new ArrayList<>();
        try {
            Connection conn = Conexao.conectar();
            if (conn != null) {
                String query = "SELECT * FROM bate_ponto WHERE funcionario_id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, funcionarioId);


                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Ponto ponto = new Ponto();
                    ponto.setId(rs.getInt("id"));
                    ponto.setFuncionario_id(rs.getInt("funcionario_id"));
                    ponto.setDataHora(rs.getTimestamp("dataHora"));
                    ponto.setTipo(rs.getString("tipo"));
                    pontos.add(ponto);
                }

                conn.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("Error: ", e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error: ", throwables.getMessage());
        }
        return pontos;
    }
}
