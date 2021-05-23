package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;

public class Login implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("Login");
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        System.out.println(name + " " + pass);
        return "/login.jsp";
//        String login = request.getParameter("login");
//        String password = request.getParameter("password");
//
    }
}
