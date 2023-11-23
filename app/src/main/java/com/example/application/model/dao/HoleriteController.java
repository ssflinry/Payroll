package com.example.application.model.dao;

import android.util.Log;

import com.example.application.model.bean.Holerite;
import com.example.application.model.connection.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoleriteController {

    public List<Holerite> getByMonthAndYear(int funcionario_id, int selectedMonth, int selectedYear) {
        List<Holerite> lista = new ArrayList<>();

        String query = "SELECT\n" +
                "    s.funcionario_id,\n" +
                "    s.adiantamentoQuinzenal,\n" +
                "    s.salarioLiquido,\n" +
                "    s.totalDesconto,\n" +
                "    s.salarioBruto\n" +
                "FROM\n" +
                "    salarios AS s\n" +
                "JOIN\n" +
                "    funcionarios AS f ON s.funcionario_id = f.id\n" +
                "JOIN\n" +
                "    usuarios AS u ON f.id = u.funcionario_id\n" +
                "JOIN\n" +
                "    holerite AS h ON s.holerite_id = h.id\n" +
                "WHERE\n" +
                "    u.funcionario_id = ?\n" +
                "    AND YEAR(h.dataPagamento) = ?\n" +
                "    AND MONTH(h.dataPagamento) = ?";


        try (Connection conn = Conexao.conectar();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, funcionario_id);
            preparedStatement.setInt(2, selectedYear);
            preparedStatement.setInt(3, selectedMonth);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Holerite holerite = new Holerite();
                    holerite.setFuncionario_id(rs.getInt("funcionario_id"));
                    holerite.setAdiantamentoQuinzenal(rs.getDouble("adiantamentoQuinzenal"));
                    holerite.setSalarioLiquido(rs.getDouble("salarioLiquido"));
                    holerite.setTotalDesconto(rs.getDouble("totalDesconto"));
                    holerite.setSalarioBruto(rs.getDouble("salarioBruto"));
                    lista.add(holerite);

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


