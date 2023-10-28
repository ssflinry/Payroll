package com.example.application.model.connection;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection conectar() throws ClassNotFoundException, SQLException {
        Connection conn = null;

        StrictMode.ThreadPolicy policy;
        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Class.forName("net.sourceforge.jtds.jdbc.Driver");

        String serverIp = "192.168.15.145:1433";
        String databaseName = "DBPayroll";
        String user = "sa";
        String pass = "1234";

        String connUrl = "jdbc:jtds:sqlserver://"+serverIp+";databaseName="+databaseName+";user="+user+";password="+pass+";";
        conn = DriverManager.getConnection(connUrl);

        return conn;
    }
}
