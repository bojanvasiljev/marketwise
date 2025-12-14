package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User createUser(User user) {
        String sql = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?) RETURNING id, created_at";
        return jdbcTemplate.queryForObject(sql,
            new Object[]{user.getUsername(), user.getEmail(), user.getPasswordHash()},
            (rs, rowNum) -> {
                user.setId(rs.getLong("id"));
                user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                return user;
            });
    }

    public User getUserById(Long id) {
        String sql = "SELECT id, username, email, password_hash, created_at FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,
                new Object[]{id},
                (rs, rowNum) -> {
                    User u = new User();
                    u.setId(rs.getLong("id"));
                    u.setUsername(rs.getString("username"));
                    u.setEmail(rs.getString("email"));
                    u.setPasswordHash(rs.getString("password_hash"));
                    u.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    return u;
                });
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET email, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getEmail(), user.getPasswordHash(), user.getId());
    }

    public void deleteUser(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
