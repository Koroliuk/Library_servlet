package ua.training.controller.command.librarian;

import ua.training.controller.command.Command;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.OrderStatus;
import ua.training.model.service.OrderService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class LibrarianHome implements Command {
    private final OrderService orderService;
    private final UserService userService;

    public LibrarianHome(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String orderId = request.getParameter("id");
        String action = request.getParameter("action");
        if (orderId == null || orderId.equals("") || action == null || action.equals("")) {
            List<User> userList = userService.findAll();
            List<Order> orderList = orderService.findAllWithStatus(OrderStatus.RECEIVED);
            request.setAttribute("orderList", orderList);
            request.setAttribute("userList", userList);
            return "/user/librarian/home.jsp";
        }
        if (action.equals("add")) {
            orderService.approveOrder(Long.parseLong(orderId));
        }
        if (action.equals("delete")) {
            orderService.cancelOrder(Long.parseLong(orderId));
        }
        List<User> userList = userService.findAll();
        List<Order> orderList = orderService.findAllWithStatus(OrderStatus.RECEIVED);
        request.setAttribute("orderList", orderList);
        request.setAttribute("userList", userList);
        return "/user/librarian/home.jsp";
    }
}
