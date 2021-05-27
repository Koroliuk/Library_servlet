package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

public class Logout implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getSession().getServletContext().getAttribute("loggedUsers");
        String login = (String) request.getSession().getAttribute("userLogin");
        loggedUsers.remove(login);
        request.getSession().setAttribute("loggedUsers", loggedUsers);
        return "redirect:/index.jsp";
    }
}
