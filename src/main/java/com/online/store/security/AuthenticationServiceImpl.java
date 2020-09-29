package com.online.store.security;

import com.online.store.exceptions.AuthenticationException;
import com.online.store.lib.Inject;
import com.online.store.lib.Service;
import com.online.store.model.User;
import com.online.store.service.UserService;
import com.online.store.util.HashUtil;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> user = userService.getByLogin(login);
        if (user.isPresent() && user.get().getPassword()
                .equals(HashUtil.hashPassword(password, user.get().getSalt()))) {
            return user.get();
        }
        throw new AuthenticationException("Incorrect login or password");
    }
}
