package ua.training.controller.command.librarian;

import ua.training.controller.command.Command;
import ua.training.model.entity.Order;
import ua.training.model.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class LibrarianHome implements Command {
    private final OrderService orderService;

    public LibrarianHome(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String orderId = request.getParameter("id");
        if (orderId == null || orderId.equals("")) {
            List<Order> orderList = orderService.findAll();
            request.setAttribute("orderList", orderList);
            return "/user/librarian/home.jsp";
        }
        orderService.approveOrder(Long.parseLong(orderId));
        List<Order> orderList = orderService.findAll();
        request.setAttribute("orderList", orderList);
        return "/user/librarian/home.jsp";
    }
}
