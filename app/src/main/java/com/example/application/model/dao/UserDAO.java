package com.example.application.model.dao;

import android.util.Log;

import com.example.application.model.bean.User;
import com.example.application.model.connection.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UserDAO {
    public User obterUser(String user, String pass) {
        try {
            Connection conn = Conexao.conectar();
            if(conn != null){
                String query = "SELECT * FROM usuarios WHERE email = '"+user+"' AND senha = '"+pass+"'";
                Statement st = null;
                st = conn.createStatement();

                ResultSet rs = st.executeQuery(query);
                while(rs.next()){
                    User usu = new User();
                    usu.setFuncionario_id(rs.getInt(2));
                    usu.setUser(rs.getString(4));
                    usu.setPass(rs.getString(5));
                    usu.setToken(rs.getString(6));
                    usu.setFirst_access(rs.getInt(7));
                    conn.close();
                    return usu;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e("Error: ", e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Error: ", throwables.getMessage());
        }
        return null;
    }
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
            Log.e("Error: ", e.getMessage());
        }

        return funcionarioId;
    }



    public boolean updateUserPasswordAndFirstAccess(String token, String novaSenha, int firstAccess) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            if (conn != null) {
                String query = "UPDATE usuarios SET senha = ?, first_access = ? WHERE token = ?";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, novaSenha);
                stmt.setInt(2, firstAccess);
                stmt.setString(3, token);

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

