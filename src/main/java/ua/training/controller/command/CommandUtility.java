package ua.training.controller.command;

import ua.training.model.entity.Role;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class CommandUtility {
    public static boolean checkUserIsLogged(HttpServletRequest request, String login) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute("loggedUsers");
        if (loggedUsers == null) {
            loggedUsers = new HashSet<>();
        }
        if (loggedUsers.stream().anyMatch(login::equals)) {
            return true;
        }
        loggedUsers.add(login);
        request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
        return false;
    }

    public static void setUserRole(HttpServletRequest request, Role role, String login) {
        HttpSession session = request.getSession();
        session.setAttribute("userLogin", login);
        session.setAttribute("role", role);
    }
}
