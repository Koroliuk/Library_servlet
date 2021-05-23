package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;

public class Signup implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("SignUp");
        String name = request.getParameter("login");
        String pass = request.getParameter("password");
        System.out.println(name+pass);
        return "/signup.jsp";
    }
}
