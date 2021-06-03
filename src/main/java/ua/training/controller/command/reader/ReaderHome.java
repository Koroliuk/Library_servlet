package ua.training.controller.command.reader;

import ua.training.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class ReaderHome implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/user/reader/home.jsp";
    }
}
