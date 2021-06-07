package ua.training.model.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import ua.training.controller.filters.LocalizationFilter;
import ua.training.model.dao.*;
import ua.training.model.entity.Book;
import ua.training.model.entity.Order;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class OrderService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public void orderBook(Order order) {
        try (OrderDao orderDao = daoFactory.createOrderDao();
            BookDao bookDao = daoFactory.createBookDao()) {
            orderDao.create(order);
            Book book = order.getBook();
            book.setCount(book.getCount()-1);
            bookDao.updateAmount(book);
        }
    }

    public List<Order> findAllWithStatus(OrderStatus orderStatus) {
        try (OrderDao orderDao = daoFactory.createOrderDao();
            UserDao userDao = daoFactory.createUserDao();
            AuthorDao authorDao = daoFactory.createAuthorDao();
            BookDao bookDao = daoFactory.createBookDao()) {
            List<Order> orderList = orderDao.findOrdersWithOrderStatus(orderStatus);
            for (Order order: orderList) {
                Optional<User> optionalUser = userDao.findById(order.getUser().getId());
                Optional<Book> optionalBook = bookDao.findByIdWithLocaled(order.getBook().getId());
                optionalUser.ifPresent(order::setUser);
                optionalBook.ifPresent(order::setBook);
                Book book = order.getBook();
                book.setAuthors(authorDao.getAuthorsByBookIdLocaled(book.getId()));
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

    public List<Order> findByUserLogin(String userLogin) {
        try (UserDao userDao = daoFactory.createUserDao();
            OrderDao orderDao = daoFactory.createOrderDao();
            BookDao bookDao = daoFactory.createBookDao();
            AuthorDao authorDao = daoFactory.createAuthorDao()) {
            Optional<User> optionalUser = userDao.findByLogin(userLogin);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                List<Order> orderList = orderDao.findUserOrdersById(user.getId());
                for (Order order: orderList) {
                    optionalUser = userDao.findById(order.getUser().getId());
                    optionalUser.ifPresent(order::setUser);
                    Optional<Book> optionalBook = bookDao.findByIdWithLocaled(order.getBook().getId());
                    optionalBook.ifPresent(order::setBook);
                    Book book = order.getBook();
                    book.setAuthors(authorDao.getAuthorsByBookIdLocaled(book.getId()));
                    if (LocalDate.now().isAfter(order.getEndDate())) {
                        if (order.getOrderStatus() == OrderStatus.APPROVED) {
                            order.setOrderStatus(OrderStatus.OVERDUE);
                            orderDao.update(order);
                        }
                        if (order.getOrderStatus() == OrderStatus.READER_HOLE) {
                            orderDao.delete(order.getId());
                        }
                    }
                }
                return orderList;
            }
        }
        return null;
    }

    public void cancelOrder(long id) {
        try (OrderDao orderDao = daoFactory.createOrderDao();
            BookDao bookDao = daoFactory.createBookDao()) {
            Optional<Order> optionalOrder = orderDao.findById(id);
            if (optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                order.setOrderStatus(OrderStatus.CANCELED);
                orderDao.update(order);
                Optional<Book> optionalBook = bookDao.findById(order.getBook().getId());
                Book book = optionalBook.get();
                book.setCount(book.getCount()+1);
                bookDao.updateAmount(book);
            }
        }
    }

    public void deleteOrder(long id) {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            orderDao.delete(id);
        }
    }

}
