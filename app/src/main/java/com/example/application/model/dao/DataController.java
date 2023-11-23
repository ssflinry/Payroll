package com.example.application.model.dao;

import android.util.Log;

import com.example.application.model.connection.Conexao;
import com.example.application.model.bean.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataController {

    public List<Data> getAll(int funcionario_id) {
        List<Data> lista = new ArrayList<>();

        String query = "SELECT " +
                "r.nomeCompleto AS nomeCompleto, " +
                "f.cargo AS cargo, " +
                "f.id AS funcionario_id, " +
                "r.cpf AS cpf, " +
                "s.pis AS pis, " +
                "r.rg AS rg, " +
                "f.dataAdmissao AS dataAdmissao " +
                "FROM usuarios AS u " +
                "JOIN funcionarios AS f ON u.funcionario_id = f.id " +
                "JOIN rg AS r ON f.id = r.funcionario_id " +
                "JOIN salarios AS s ON f.id = s.funcionario_id " +
                "WHERE u.funcionario_id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, funcionario_id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Data data = new Data();
                    data.setNomeCompleto(rs.getString("nomeCompleto"));
                    data.setCargo(rs.getString("cargo"));
                    data.setFuncionario_id(rs.getInt("funcionario_id"));
                    data.setCpf(rs.getString("cpf"));
                    data.setPis(rs.getString("pis"));
                    data.setRg(rs.getString("rg"));
                    data.setDataAdmissao(rs.getString("dataAdmissao"));

                    lista.add(data);
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("Error: ", e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error: ", throwables.getMessage());
        }

        return lista;
    }
}