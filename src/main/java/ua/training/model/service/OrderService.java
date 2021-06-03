package ua.training.model.service;

import ua.training.model.dao.*;
import ua.training.model.entity.Book;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.OrderStatus;

import java.util.List;
import java.util.Optional;

public class OrderService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public void orderBook(Order order, Book book) {
        //TODO: dec book count
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.create(order);
        }
    }

    public List<Order> findAll() {
        try (OrderDao orderDao = daoFactory.createOrderDao();
            UserDao userDao = daoFactory.createUserDao();
            AuthorDao authorDao = daoFactory.createAuthorDao();
            BookDao bookDao = daoFactory.createBookDao()) {
            List<Order> orderList = orderDao.findAll();
            for (Order order: orderList) {
                Optional<User> optionalUser = userDao.findById(order.getUser().getId());
                Optional<Book> optionalBook = bookDao.findById(order.getBook().getId());
                optionalUser.ifPresent(order::setUser);
                optionalBook.ifPresent(order::setBook);
                Book book = order.getBook();
                book.setAuthors(authorDao.getAuthorsByBookId(book.getId()));
            }
            return orderList;
        }
    }

    public void approveOrder(long orderId) {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            Optional<Order> optionalOrder = orderDao.findById(orderId);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                order.setOrderStatus(OrderStatus.APPROVED);
                orderDao.update(order);
            }
        }
    }
}
