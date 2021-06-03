package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

public class Logout implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute("loggedUsers");
        String login = (String) request.getSession().getAttribute("userLogin");
        if (loggedUsers != null) {
            loggedUsers.remove(login);
        }
        request.getSession().getServletContext().setAttribute("loggedUsers", loggedUsers);
        request.getSession().setAttribute("userLogin", null);
        request.getSession().setAttribute("role", null);
        return "redirect:/index.jsp";
    }
}
