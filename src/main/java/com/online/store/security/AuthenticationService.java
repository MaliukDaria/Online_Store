package com.online.store.security;

import com.online.store.exceptions.AuthenticationException;
import com.online.store.model.User;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;
}
