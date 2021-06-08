package ua.training.controller.command.reader;

import ua.training.controller.command.Command;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.model.service.OrderService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Supplier;

public class ReaderHome implements Command {
    private final OrderService orderService;
    private final UserService userService;

    public ReaderHome(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String userLogin = (String) request.getSession().getAttribute("userLogin");
        String orderId = request.getParameter("orderId");
        String tab = request.getParameter("tab");
        User user = userService.findByLogin(userLogin).orElseThrow(RuntimeException::new);
        if (orderId == null || orderId.equals("") || tab == null || tab.equals("")) {
            List<Order> orderList = orderService.findByUserId(user.getId());
            request.setAttribute("orderList", orderList);
            return "/user/reader/home.jsp";
        }
        boolean result = orderService.deleteOrder(Long.parseLong(orderId));
        if (!result) {
            return "/error/error.jsp";
        }
        List<Order> orderList = orderService.findByUserId(user.getId());
        request.setAttribute("orderList", orderList);
        return "/user/reader/home.jsp?tab="+tab;
    }
}
