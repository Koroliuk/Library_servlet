package ua.training.model.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.UserDao;
import ua.training.model.entity.User;

import java.util.Optional;

public class UserService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public void singUpUser(User user) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.create(user);
        }
    }

    public Optional<User> getUserByLogin(String login) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findByLogin(login);
        }
    }
}
