package ua.training.controller.command.admin;

import ua.training.controller.command.Command;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.Role;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Pattern;

public class AddLibrarian implements Command {
    private final UserService userService;

    public AddLibrarian(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login == null  || password == null || login.equals("") || password.equals("")) {
            return "/user/admin/librarianForm.jsp";
        }
        String loginPattern = "^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{4,20}$";
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,30}$";
        boolean isLoginValid = Pattern.matches(loginPattern, login);
        boolean isPasswordValid = Pattern.matches(passwordPattern, password);
        if (!isLoginValid || !isPasswordValid) {
            return "/user/admin/librarianForm.jsp?validError=true";
        }
        Optional<User> optionalUser = userService.findByLogin(login);
        if (optionalUser.isPresent()) {
            return "/user/admin/librarianForm.jsp?loginError=true";
        }
        User user = new User.Builder()
                .login(login)
                .password_hash(password)
                .role(Role.LIBRARIAN)
                .isBlocked(false)
                .build();
        boolean result = userService.singUpUser(user);
        if (!result) {
            return "/error/error.jsp";
        } else {
            return "/user/admin/librarianForm.jsp?successEvent=true";
        }
    }
}
