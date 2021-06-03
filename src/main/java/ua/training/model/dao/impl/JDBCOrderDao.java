package ua.training.model.dao.impl;

import ua.training.model.dao.OrderDao;
import ua.training.model.dao.mapper.OrderMapper;
import ua.training.model.entity.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCOrderDao implements OrderDao {
    private final Connection connection;

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Order entity) {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.CREATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getUser().getId());
            statement.setLong(2, entity.getBook().getId());
            statement.setObject(3, entity.getStartDate());
            statement.setObject(4, entity.getEndDate());
            statement.setString(5, entity.getOrderStatus().name());
            if (statement.executeUpdate() > 0) {
                try (ResultSet resultSet = statement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        entity.setId(resultSet.getInt(1));
                    }
                }
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Order> findById(long id) {
        try (PreparedStatement statement = connection.prepareStatement(SQLConstants.GET_ORDER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                OrderMapper mapper = new OrderMapper();
                Order order = mapper.extractFromResultSet(resultSet);
                return Optional.of(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        List<Order> orderList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SQLConstants.GET_ALL_ORDERS);
            while (resultSet.next()) {
                OrderMapper mapper = new OrderMapper();
                Order order = mapper.extractFromResultSet(resultSet);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public void update(Order entity) {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQLConstants.UPDATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getUser().getId());
            statement.setLong(2, entity.getBook().getId());
            statement.setObject(3, entity.getStartDate());
            statement.setObject(4, entity.getEndDate());
            statement.setString(5, entity.getOrderStatus().name());
            statement.setLong(6, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
