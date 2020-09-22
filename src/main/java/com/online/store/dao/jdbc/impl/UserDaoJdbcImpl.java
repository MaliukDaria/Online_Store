package com.online.store.dao.jdbc.impl;

import com.online.store.dao.UserDao;
import com.online.store.exceptions.DataProcessingException;
import com.online.store.lib.Dao;
import com.online.store.model.Role;
import com.online.store.model.User;
import com.online.store.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public User create(User user) {
        String createQuery = "INSERT INTO users (login, password) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     createQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            User createdUser = new User(user.getLogin(), user.getPassword());
            createdUser.setRoles(user.getRoles());
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                createdUser.setId(resultSet.getLong(1));
            }
            setUserRoles(createdUser, createdUser.getRoles());
            return createdUser;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create user " + user.getName(), e);
        }
    }

    @Override
    public User update(User user) {
        String updateQuery = "UPDATE users SET login = ?, password = ?, name = ? " +
                "WHERE user_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setLong(4, user.getId());
            int numberOfUpdatedRows = statement.executeUpdate();
            if (numberOfUpdatedRows != 0) {
                deleteUserRoles(user);
                setUserRoles(user, user.getRoles());
                return user;
            }
            throw new DataProcessingException(
                    "Can't find user with id: " + user.getId() + " to update it");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user with id: " + user.getId(), e);
        }
    }

    @Override
    public Optional<User> getById(Long id) {
        String getByIdQuery = "SELECT user_id, login, password, name FROM users " +
                "WHERE user_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(getByIdQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUser(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user by id: " + id, e);
        }
    }

    @Override
    public Optional<User> getByLogin(String login) {
        String getByLoginQuery = "SELECT user_id, login, password, name FROM users " +
                "WHERE login = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(getByLoginQuery)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUser(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user by login: " + login, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteByIdQuery = "UPDATE users SET isDeleted = true " +
                "WHERE user_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteByIdQuery)) {
            deleteUserRoles(getById(id).get());
            statement.setLong(1, id);
            int numberOfDeletedRows = statement.executeUpdate();
            return numberOfDeletedRows != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user by id: " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String getByIdQuery = "SELECT * FROM users WHERE isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(getByIdQuery)) {
            ResultSet resultSet = statement.executeQuery();
            List<User> allUsers = new ArrayList<>();
            while (resultSet.next()) {
                allUsers.add(getUser(resultSet));
            }
            return allUsers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all users", e);
        }
    }

    private Long getRoleId(Role role) {
        String setRoleQuery = "SELECT role_id FROM roles WHERE name = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(setRoleQuery)) {
            statement.setString(1, String.valueOf(role.getRoleName()));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("role_id");
            }
            throw new DataProcessingException("Can't get id for role name: " + role.getRoleName());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get role id ", e);
        }
    }

    private boolean setUserRoles(User user, Set<Role> roles) {
        String setRoleQuery = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(setRoleQuery)) {
            statement.setLong(1, user.getId());
            int generalNumberOfCreatedRoles = 0;
            for (Role role : roles) {
                statement.setLong(2, getRoleId(role));
                int numberOfCreatedRoles = statement.executeUpdate();
                generalNumberOfCreatedRoles += numberOfCreatedRoles;
            }
            if (generalNumberOfCreatedRoles == roles.size()) {
                return true;
            }
            throw new DataProcessingException("Not all roles have been assigned to the user " + user.getName());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't set role to user " + user.getName(), e);
        }
    }

    private boolean deleteUserRoles(User user) {
        String deleteUsersRolesQuery = "DELETE FROM users_roles WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteUsersRolesQuery)) {
            statement.setLong(1, user.getId());
            int numberOfDeletedRoles = statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete users roles", e);
        }
    }

    private Set<Role> getUserRoles(Long userId) {
        String getUserRolesQuery = "SELECT name FROM roles " +
                "INNER JOIN users_roles ON roles.role_id = users_roles.role_id " +
                "WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(getUserRolesQuery)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            Set<Role> userRoles = new HashSet<>();
            while (resultSet.next()) {
                Role role = Role.of(resultSet.getString("name"));
                userRoles.add(role);
            }
            return userRoles;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get users roles", e);
        }
    }

    private User getUser(ResultSet resultSet) {
        try {
            long user_id = resultSet.getLong("user_id");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            String name = resultSet.getString("name");
            Set<Role> userRoles = getUserRoles(user_id);
            User user = new User(login, password);
            user.setId(user_id);
            user.setName(name);
            user.setRoles(userRoles);
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user from resultSet", e);
        }
    }
}
