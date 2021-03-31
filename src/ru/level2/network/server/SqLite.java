package ru.level2.network.server;

import java.sql.*;

//in progect add ProgectStructure -> libraries SqLite sqlite-jdbc-3.21.0.1.jar

public class SqLite {
    Connection connSql;

    public SqLite(){
        {
            try {
                Class.forName("org.sqlite.JDBC");

            } catch (ClassNotFoundException throwables) {
                //
                throwables.printStackTrace();
            }
        }

    }

    public void RenameUser(String oldName, String newName) {
        try {
            connSql = DriverManager.getConnection("JDBC:sqlite:./server.db");
            PreparedStatement querySqlAuthFind = connSql.prepareStatement("UPDATE auth SET nikname = ? WHERE nikname = ?");
            querySqlAuthFind.setString(1, newName);
            querySqlAuthFind.setString(2, oldName);
            querySqlAuthFind.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connSql.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public String  AuthFind( String login, String pass) {
        try {
            connSql = DriverManager.getConnection("JDBC:sqlite:./server.db");
            PreparedStatement querySqlAuthFind = connSql.prepareStatement("SELECT * FROM auth WHERE user = ? AND password = ? LIMIT 1");
            querySqlAuthFind.setString(1, login);
            querySqlAuthFind.setString(2, pass);
            ResultSet resultSql = querySqlAuthFind.executeQuery();
            resultSql.next();
            return resultSql.getString(3);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            try {
                connSql.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return "";
    }

}
