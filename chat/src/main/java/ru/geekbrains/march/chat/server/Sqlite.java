package ru.geekbrains.march.chat.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sqlite {
    private static final Logger LOGGER = LogManager.getLogger(Sqlite.class);
    Connection connSql;

    public Sqlite(){
        {
            try {
                LOGGER.trace("load Class org.sqlite.JDBC");
                Class.forName("org.sqlite.JDBC");

            } catch (ClassNotFoundException throwables) {
                LOGGER.throwing(Level.ERROR, throwables);
            }
        }

    }

    public void RenameUser(String oldName, String newName) {
        try {
            LOGGER.debug("RenameUser");
            connSql = DriverManager.getConnection("JDBC:sqlite:server.db");
            PreparedStatement querySqlAuthFind = connSql.prepareStatement("UPDATE auth SET nikname = ? WHERE nikname = ?");
            LOGGER.trace(querySqlAuthFind);
            querySqlAuthFind.setString(1, newName);
            LOGGER.trace(querySqlAuthFind);
            querySqlAuthFind.setString(2, oldName);
            LOGGER.trace(querySqlAuthFind);
            querySqlAuthFind.executeUpdate();
            LOGGER.debug("querySqlAuthFind executeUpdate");
            LOGGER.info("querySqlAuthFind executeUpdate");
        } catch (SQLException throwables) {
            LOGGER.throwing(Level.ERROR, throwables);
        } finally {
            try {
                connSql.close();
            } catch (SQLException throwables) {
                LOGGER.throwing(Level.ERROR, throwables);
            }
        }
    }

    public String  AuthFind( String login, String pass) {
        try {
            LOGGER.debug("AuthFind");
            connSql = DriverManager.getConnection("JDBC:sqlite:./server.db");
            PreparedStatement querySqlAuthFind = connSql.prepareStatement("SELECT * FROM auth WHERE user = ? AND password = ? LIMIT 1");
            LOGGER.trace(querySqlAuthFind);
            querySqlAuthFind.setString(1, login);
            LOGGER.trace(querySqlAuthFind);
            querySqlAuthFind.setString(2, pass);
            LOGGER.trace(querySqlAuthFind);
            ResultSet resultSql = querySqlAuthFind.executeQuery();
            resultSql.next();
            LOGGER.trace(resultSql.getStatement());
            LOGGER.debug("Find " + resultSql.getString(3));
            return resultSql.getString(3);

        } catch (SQLException throwables) {
            LOGGER.throwing(Level.ERROR, throwables);
        }
        finally {
            try {
                connSql.close();
                LOGGER.info("SQL close");
            } catch (SQLException throwables) {
                LOGGER.throwing(Level.ERROR, throwables);
            }
        }
        return "";
    }

}

