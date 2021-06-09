package ua.training.controller.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;

public class SessionListener implements HttpSessionListener {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HashSet<String> loggedUsers = (HashSet<String>) httpSessionEvent
                .getSession().getServletContext()
                .getAttribute("loggedUsers");
        String login = (String) httpSessionEvent.getSession()
                .getAttribute("userLogin");
        if (loggedUsers != null) {
            loggedUsers.remove(login);
        }
        httpSessionEvent.getSession().setAttribute("loggedUsers", loggedUsers);
        logger.info("Destroying a user session with login "+login);
    }
}
