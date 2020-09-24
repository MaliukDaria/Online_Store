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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public User create(User user) {
        User createdUser = addUserToUsersTable(user);
        insertUserRolesRelation(createdUser);
        return createdUser;
    }

    @Override
    public User update(User user) {
        User updatedUser = updateUserInUsersTable(user);
        deleteUserRolesRelation(updatedUser);
        insertUserRolesRelation(updatedUser);
        return updatedUser;
    }

    @Override
    public Optional<User> getById(Long id) {
        Optional<User> user = getUserFromUsersTable(id);
        if (user.isPresent()) {
            user.get().setRoles(getUserRoles(id));
            return user;
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getByLogin(String login) {
        Optional<User> user = getUserFromUsersTable(login);
        if (user.isPresent()) {
            user.get().setRoles(getUserRoles(user.get().getId()));
            return user;
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return deleteUserFromUsersTable(id);
    }

    @Override
    public List<User> getAll() {
        List<User> allUsersFromUsersTable = getAllUsersFromUsersTable();
        for (User user: allUsersFromUsersTable) {
            user.setRoles(getUserRoles(user.getId()));
        }
        return allUsersFromUsersTable;
    }

    private List<User> getAllUsersFromUsersTable() {
        String getUserByIdQuery = "SELECT * FROM users WHERE isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getUserByIdQuery)) {
            ResultSet resultSet = statement.executeQuery();
            List<User> allUsers = new ArrayList<>();
            while (resultSet.next()) {
                allUsers.add(getUserFromResultSet(resultSet));
            }
            return allUsers;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all users", e);
        }
    }

    private User addUserToUsersTable(User user) {
        String createUserQuery = "INSERT INTO users (login, password) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        createUserQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            User createdUser = new User(user.getLogin(), user.getPassword());
            createdUser.setRoles(user.getRoles());
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                createdUser.setId(resultSet.getLong(1));
            }
            return createdUser;
        } catch (SQLException e) {
            throw new DataProcessingException(
                    "Can't create user with login: " + user.getLogin(), e);
        }
    }

    private User updateUserInUsersTable(User user) {
        String updateUserQuery = "UPDATE users SET login = ?, password = ?, name = ? "
                + "WHERE user_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(updateUserQuery)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setLong(4, user.getId());
            int numberOfUpdatedRows = statement.executeUpdate();
            if (numberOfUpdatedRows != 0) {
                return user;
            }
            throw new DataProcessingException(
                    "Can't find user with id: " + user.getId() + " to update it");
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user with id: " + user.getId(), e);
        }
    }

    private Optional<User> getUserFromUsersTable(Long id) {
        String getUserByIdQuery = "SELECT user_id, login, password, name FROM users "
                + "WHERE user_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getUserByIdQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user by id: " + id, e);
        }
    }

    private Optional<User> getUserFromUsersTable(String login) {
        String getUserByLoginQuery = "SELECT user_id, login, password, name FROM users "
                + "WHERE login = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getUserByLoginQuery)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user by login: " + login, e);
        }
    }

    private boolean deleteUserFromUsersTable(Long id) {
        String deleteUserByIdQuery = "UPDATE users SET isDeleted = true "
                + "WHERE user_id = ? AND isDeleted = false";
        try (Connection connection = ConnectionUtil.getConnection();
                 PreparedStatement statement = connection.prepareStatement(deleteUserByIdQuery)) {
            statement.setLong(1, id);
            int numberOfDeletedRows = statement.executeUpdate();
            return numberOfDeletedRows != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user by id: " + id, e);
        }
    }

    private Long getRoleIdByName(Role role) {
        String getRoleIdQuery = "SELECT role_id FROM roles WHERE name = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getRoleIdQuery)) {
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

    private boolean insertUserRolesRelation(User user) {
        for (Role role: user.getRoles()) {
            role.setId(getRoleIdByName(role));
        }
        String setRoleQuery = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(setRoleQuery)) {
            statement.setLong(1, user.getId());
            int generalNumberOfCreatedRoles = 0;
            for (Role role : user.getRoles()) {
                statement.setLong(2, role.getId());
                int numberOfCreatedRoles = statement.executeUpdate();
                generalNumberOfCreatedRoles += numberOfCreatedRoles;
            }
            if (generalNumberOfCreatedRoles == user.getRoles().size()) {
                return true;
            }
            throw new DataProcessingException(
                    "Not all roles have been assigned to the user " + user.getName());
        } catch (SQLException e) {
            throw new DataProcessingException("Can't set role to user " + user.getName(), e);
        }
    }

    private boolean deleteUserRolesRelation(User user) {
        String deleteUsersRolesQuery = "DELETE FROM users_roles WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteUsersRolesQuery)) {
            statement.setLong(1, user.getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete users roles", e);
        }
    }

    private Set<Role> getUserRoles(Long userId) {
        String getUserRolesQuery = "SELECT r.role_id, r.name FROM roles r "
                + "INNER JOIN users_roles ur ON r.role_id = ur.role_id "
                + "WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(getUserRolesQuery)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            Set<Role> userRoles = new HashSet<>();
            while (resultSet.next()) {
                Role role = Role.of(resultSet.getString("name"));
                role.setId(resultSet.getLong("role_id"));
                userRoles.add(role);
            }
            return userRoles;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get users roles", e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) {
        try {
            long userId = resultSet.getLong("user_id");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            String name = resultSet.getString("name");
            User user = new User(login, password);
            user.setId(userId);
            user.setName(name);
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get user from resultSet", e);
        }
    }
}
