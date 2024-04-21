package org.example.app.repository.impl;

import org.example.app.database.DBConn;
import org.example.app.entity.User;
import org.example.app.repository.AppRepository;
import org.example.app.utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository implements AppRepository<User> {

    private final static String TABLE_USERS = "users";
    private static final Logger LOGGER =
            Logger.getLogger(UserRepository.class.getName());

    @Override
    public String create(User user) {
        String sql = "INSERT INTO " + TABLE_USERS + " (name, email) VALUES(?, ?)";
        try (PreparedStatement pstmt = DBConn.connect().prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.executeUpdate();
            return Constants.DATA_INSERT_MSG;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            return e.getMessage();
        }
    }

    @Override
    public Optional<List<User>> read() {
        try (Statement stmt = DBConn.connect().createStatement()) {
            List<User> list = new ArrayList<>();
            String sql = "SELECT id, name, email FROM " + TABLE_USERS;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"))
                );
            }
            return Optional.of(list);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> readById(Long id) {
        String sql = "SELECT id, name, email FROM " + TABLE_USERS +
                " WHERE id = ?";
        try (PreparedStatement pst = DBConn.connect().prepareStatement(sql)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            User user = new User();
            long entityId = rs.getLong("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            if (entityId == 0 | name == null | email == null)
                user = null;
            else {
                user.setId(entityId);
                user.setName(name);
                user.setEmail(email);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            return Optional.empty();
        }
    }

    @Override
    public String update(User user) {
        String sql = "UPDATE " + TABLE_USERS + " SET name = ?, email = ? WHERE id = ?";
        try (PreparedStatement pstmt = DBConn.connect().prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setLong(3, user.getId());
            pstmt.executeUpdate();
            return Constants.DATA_UPDATE_MSG;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            return e.getMessage();
        }
    }

    @Override
    public String delete(Long id) {
        String sql = "DELETE FROM " + TABLE_USERS + " WHERE id = ?";
        try (PreparedStatement stmt = DBConn.connect().prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            return Constants.DATA_DELETE_MSG;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            return e.getMessage();
        }
    }

    public boolean isIdExists(Long id) {
        String sql = "SELECT COUNT(id) FROM " + TABLE_USERS +
                " WHERE id = ?";
        try {
            PreparedStatement pst = DBConn.connect().prepareStatement(sql);
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, Constants.LOG_DB_ERROR_MSG);
            return false;
        }
        return false;
    }
}
