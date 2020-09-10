package com.online.store.dao;

import com.online.store.model.User;
import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {
    Optional<User> getByLogin(String login);
}
