package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;

public class Logout implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("Logout");
        return "redirect:/index.jsp";
    }
}
