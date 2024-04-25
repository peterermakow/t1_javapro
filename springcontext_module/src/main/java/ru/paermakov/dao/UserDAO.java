package ru.paermakov.dao;

import ru.paermakov.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.paermakov.config.DataSource.*;

public class UserDAO {

    private final String GET_ALL_USERS_QUERY = "SELECT id, username FROM users";
    private final String GET_USER_BY_ID_QUERY = "SELECT id, username FROM users WHERE id = ?";
    private final String SAVE_USER_QUERY = "INSERT INTO users VALUES (?,?)";
    private final String DELETE_USER_BY_ID_QUERY = "DELETE FROM users WHERE id = ?";

    public List<User> getAllUsers() {
        List<User> users;
        try (Connection connection = getConnection()) {
            PreparedStatement pst = connection.prepareStatement(GET_ALL_USERS_QUERY);
            ResultSet rs = pst.executeQuery();
            users = new ArrayList<>();
            User user;
            while (rs.next()) {
                user = new User(rs.getLong("id"), rs.getString("username"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public Optional<User> getUserById(Long id)  {
        try (Connection connection = getConnection()) {
            PreparedStatement pst = connection.prepareStatement(GET_USER_BY_ID_QUERY);
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(rs.getLong("id"), rs.getString("username")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public void saveUser(User user) {
        try (Connection connection = getConnection()) {
            PreparedStatement pst = connection.prepareStatement(SAVE_USER_QUERY);
            pst.setLong(1, user.getId());
            pst.setString(2, user.getUsername());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUserById(Long id) {
        try (Connection connection = getConnection()) {
            PreparedStatement pst = connection.prepareStatement(DELETE_USER_BY_ID_QUERY);
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
