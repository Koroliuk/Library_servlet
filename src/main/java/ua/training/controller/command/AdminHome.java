package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;

public class AdminHome implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/user/admin/home.jsp";
    }
}
