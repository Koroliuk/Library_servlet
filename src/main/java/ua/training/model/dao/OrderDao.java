package ua.training.model.dao;

import ua.training.model.entity.Order;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {
    List<Order> findUserOrdersById(long id);
}

