package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;

public class Signup implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("SignUp");
        return "/signup.jsp";
    }
}
