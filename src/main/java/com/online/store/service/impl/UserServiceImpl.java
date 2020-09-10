package com.online.store.service.impl;

import com.online.store.dao.UserDao;
import com.online.store.lib.Inject;
import com.online.store.lib.Service;
import com.online.store.model.User;
import com.online.store.service.UserService;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User create(User user) {
        return userDao.create(user);
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id).get();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public User getByLogin(String login) {
        return userDao.getByLogin(login).get();
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }
}
