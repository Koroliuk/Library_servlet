package ua.training.model.service;

import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.UserDao;
import ua.training.model.entity.User;

public class UserService {
    DaoFactory daoFactory = DaoFactory.getInstance();

    public void singUpUser(User user) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.create(user);
        }
    }
}
