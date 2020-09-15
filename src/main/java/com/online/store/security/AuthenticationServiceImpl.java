package com.online.store.security;

import com.online.store.exceptions.AuthenticationException;
import com.online.store.lib.Inject;
import com.online.store.lib.Service;
import com.online.store.model.User;
import com.online.store.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User user = userService.getByLogin(login).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        throw new AuthenticationException("Incorrect login or password");
    }
}
