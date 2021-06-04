package ua.training.controller.command.reader;

import ua.training.controller.command.Command;
import ua.training.model.entity.Order;
import ua.training.model.service.OrderService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ReaderHome implements Command {
    private final UserService userService;
    private final OrderService orderService;

    public ReaderHome(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String userLogin = (String) request.getSession().getAttribute("userLogin");
        List<Order> orderList = orderService.findByUserLogin(userLogin);
        request.setAttribute("orderList", orderList);
        return "/user/reader/home.jsp";
    }
}
