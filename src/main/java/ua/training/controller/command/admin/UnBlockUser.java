package ua.training.controller.command.admin;

import ua.training.controller.command.Command;
import ua.training.model.entity.User;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class UnBlockUser implements Command {
    private final UserService userService;

    public UnBlockUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String userId = request.getParameter("id");
        if (userId == null || userId.equals("")) {
            return "/error/error.jsp";
        }
        Optional<User> optionalUser = userService.findById(Long.parseLong(userId));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userService.unBlockUser(user);
            return "redirect:/admin/home";
        }
        return "/error/error.jsp";
    }}
