package ua.training.controller.command;

import ua.training.model.entity.Role;
import ua.training.model.entity.User;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class Login implements Command {
    private final UserService userService;

    public Login(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login == null || login.equals("") || password == null || password.equals("")) {
            return "/login.jsp";
        }
        if (CommandUtility.checkUserIsLogged(request, login)) {
            return "/WEB-INF/error.jsp";
        }
        Optional<User> optionalUser = userService.getUserByLogin(login);
        if (!optionalUser.isPresent()) {
            return "/WEB-INF/error.jsp";
        }
        User user = optionalUser.get();
        if (!password.equals(user.getPassword_hash())) {
            return "/login.jsp";
        }
        if (user.getRole().equals(Role.READER)) {
            CommandUtility.setUserRole(request, Role.READER, login);
            return "/WEB-INF/reader/reader.jsp";
        } else if (user.getRole().equals(Role.LIBRARIAN)) {
            CommandUtility.setUserRole(request, Role.LIBRARIAN, login);
            return "/WEB-INF/librarian/librarian.jsp";
        } else if (user.getRole().equals(Role.ADMIN)) {
            CommandUtility.setUserRole(request, Role.ADMIN, login);
            return "/WEB-INF/admin/admin.jsp";
        } else {
            return "login.jsp";
        }
    }
}
