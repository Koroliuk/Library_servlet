package ua.training.model.dao.impl;

import com.sun.corba.se.impl.util.RepositoryIdCache;
import ua.training.model.dao.UserDao;
import ua.training.model.entity.Role;
import ua.training.model.entity.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao implements UserDao {
    private final Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void create(User entity) {
        String query = "INSERT INTO users (login, password_hash, user_role, is_blocked) VALUES (?, ?, ?::\"role\", ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword_hash());
            statement.setString(3, entity.getRole().name());
            statement.setBoolean(4, entity.isBlocked());
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
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }

    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users WHERE login=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                String password = resultSet.getString(3);
                Role role = Role.valueOf(resultSet.getString(4));
                boolean is_blocked = resultSet.getBoolean(5);
                User user = new User(login, password, role, is_blocked);
                user.setId(id);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
