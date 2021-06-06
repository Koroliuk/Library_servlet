package ua.training.controller.command.librarian;

import ua.training.controller.command.Command;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.OrderStatus;
import ua.training.model.service.OrderService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GetReaderBooks implements Command {
    private final OrderService orderService;
    private final UserService userService;

    public GetReaderBooks(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (userId == null || userId.equals("")) {
            return "redirect:/librarian/home?tab=2";
        }
        Optional<User> optionalUser = userService.findById(Long.parseLong(userId));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Order> orderList = orderService.findByUserLogin(user.getLogin()).stream()
                    .filter((order) -> order.getOrderStatus() != OrderStatus.APPROVED
                            || order.getOrderStatus() != OrderStatus.OVERDUE).collect(Collectors.toList());
            request.setAttribute("orderList", orderList);
            request.setAttribute("user", user);
            return "/user/librarian/readerBooks.jsp";
        }
        return "/error/error,jsp";
    }
}
