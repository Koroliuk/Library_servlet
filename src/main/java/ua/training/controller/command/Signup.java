package ua.training.controller.command;

import ua.training.model.entity.Role;
import ua.training.model.entity.User;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class Signup implements Command {
    private final UserService userService;

    public Signup(UserService userService) {
        this.userService = userService;
    }


    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login == null || login.equals("") || password == null  || password.equals("")) {
            return "/signup.jsp";
        }
        User user = new User(login, password, Role.READER, false);
        userService.singUpUser(user);
        return "/signup.jsp";
    }
}
