package ua.training.model.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.UserDao;
import ua.training.model.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public void singUpUser(User user) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> findByLogin(String login) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findByLogin(login);
        }
    }

    public List<User> findAll() {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findAll();
        }
    }

    public void delete(long id) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void blockUser(User user) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            user.setBlocked(true);
            userDao.update(user);
        }
    }

    public void unBlockUser(User user) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            user.setBlocked(false);
            userDao.update(user);
        }
    }

    public Optional<User> findById(long id) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findById(id);
        }
    }
}
