package com.online.store.service;

import com.online.store.model.User;
import java.util.Optional;

public interface UserService extends GenericService<User, Long> {
    User update(User user);

    Optional<User> getByLogin(String login);
}
