package ua.training.controller.command;

import javax.servlet.http.HttpServletRequest;

public class LibrarianHome implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/user/librarian/home.jsp";
    }
}
