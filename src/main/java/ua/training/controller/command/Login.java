package ua.training.controller.command;

import com.sun.net.httpserver.HttpContext;

import javax.servlet.http.HttpServletRequest;

public class Login implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("Login");
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        System.out.println(name + " " + pass);
        return "/user.jsp";
//        String login = request.getParameter("login");
//        String password = request.getParameter("password");
//
    }
}
