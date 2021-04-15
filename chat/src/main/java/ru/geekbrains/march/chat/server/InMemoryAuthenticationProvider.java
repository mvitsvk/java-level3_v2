package ru.geekbrains.march.chat.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InMemoryAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOGGER = LogManager.getLogger(InMemoryAuthenticationProvider.class);
    Sqlite sql;

    public InMemoryAuthenticationProvider() {
        LOGGER.info("init SQL");
        sql = new Sqlite();
        LOGGER.debug("init SQL complite");
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        LOGGER.info("AuthFind user" + login);
        return this.sql.AuthFind( login, password);
    }

    @Override
    public void changeNickname(String oldNickname, String newNickname) {
        LOGGER.info("RenameUser " + oldNickname + " => " + newNickname);
        this.sql.RenameUser(oldNickname, newNickname);
    }
}
