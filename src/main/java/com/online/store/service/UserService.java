package com.online.store.service;

import com.online.store.model.User;

public interface UserService extends GenericService<User, Long> {
    User update(User user);

    User getByLogin(String login);
}
