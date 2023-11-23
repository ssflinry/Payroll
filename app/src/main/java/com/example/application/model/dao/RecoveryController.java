package com.example.application.model.dao;

import android.util.Log;

import com.example.application.model.connection.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecoveryController {

    public boolean isEmailExists(String email) {
        boolean emailExists = false;

        try {
            Connection conn = Conexao.conectar();
            if (conn != null) {
                String query = "SELECT COUNT(*) AS count FROM usuarios WHERE email = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, email);

                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt("count");
                    emailExists = count > 0;
                }

                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return emailExists;
    }

    public static String obterTokenPorEmail(String email) {
        String token = null;

        try {
            Connection conn = Conexao.conectar();
            if (conn != null) {
                String query = "SELECT token FROM usuarios WHERE email = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, email);

                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    token = rs.getString("token");
                }

                conn.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static boolean updateFirstAccess(String token, int firstAccess) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            if (conn != null) {
                String query = "UPDATE usuarios SET first_access = ? WHERE token = ?";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, firstAccess);
                stmt.setString(2, token);

                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.e("Error: ", e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                Log.e("Error: ", e.getMessage());
            }
        }
        return false;
    }
}
