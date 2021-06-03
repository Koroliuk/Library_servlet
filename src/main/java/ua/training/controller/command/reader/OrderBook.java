package ua.training.controller.command.reader;

import ua.training.controller.command.Command;
import ua.training.model.entity.Book;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.OrderStatus;
import ua.training.model.service.BookService;
import ua.training.model.service.OrderService;
import ua.training.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Optional;

public class OrderBook implements Command {
    private final UserService userService;
    private final BookService bookService;
    private final OrderService orderService;

    public OrderBook(UserService userService, BookService bookService, OrderService orderService) {
        this.userService = userService;
        this.bookService = bookService;
        this.orderService = orderService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String bookId = request.getParameter("bookId");
        String userLogin = request.getParameter("userLogin");
        String orderType = request.getParameter("orderType");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        Optional<User> optionalUser = userService.findByLogin(userLogin);
        Optional<Book> optionalBook = bookService.findById(Long.parseLong(bookId));
        if (!optionalUser.isPresent())  {
            return "/error/error.jsp";
        }
        if (!optionalBook.isPresent()) {
            return "/error/error.jsp";
        }

        User user = optionalUser.get();
        Book book = optionalBook.get();
        request.setAttribute("user", user);
        request.setAttribute("book", book);

        if (bookId.equals("") || userLogin == null || userLogin.equals("")) {
            return "/user/reader/orderBook.jsp";
        }
        if (orderType == null || orderType.equals("") || startDate == null || startDate.equals("") || endDate == null || endDate.equals("")) {
            return "/user/reader/orderBook.jsp";
        }

        //TODO: date server validation
        OrderStatus status;
        if (orderType.equals("subscription")) {
            status = OrderStatus.RECEIVED;
        } else {
            status = OrderStatus.READER_HOLE;
        }
        Order order = new Order.Builder()
                .book(book)
                .user(user)
                .startDate(LocalDate.parse(startDate))
                .endDate(LocalDate.parse(endDate))
                .orderStatus(status)
                .build();
        orderService.orderBook(order, book);
        return "/user/reader/orderBook.jsp?success=true";
    }
}
