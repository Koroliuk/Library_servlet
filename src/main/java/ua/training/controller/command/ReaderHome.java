package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;

public class ReaderHome implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/user/reader/home.jsp";
    }
}
