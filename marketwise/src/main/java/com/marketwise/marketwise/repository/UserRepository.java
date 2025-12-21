package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.common.MarketWiseSQL;
import com.marketwise.marketwise.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

  private final JdbcTemplate jdbcTemplate;

  public UserRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static final RowMapper<User> userMapper = (rs, rowNum) -> new User(
      rs.getLong("id"),
      rs.getString("username"),
      rs.getString("email"),
      rs.getString("password_hash"),
      rs.getTimestamp("created_at").toInstant());

  public User getUserById(Long id) {
    return jdbcTemplate.queryForObject(MarketWiseSQL.GET_USER_BY_ID,
        new Object[] {id},
        (rs, rowNum) -> {
          User u = new User();
          u.setId(rs.getLong("id"));
          u.setUsername(rs.getString("username"));
          u.setEmail(rs.getString("email"));
          u.setPasswordHash(rs.getString("password_hash"));
          u.setCreatedAt(rs.getTimestamp("created_at").toInstant());
          return u;
        });
  }

  public List<User> getUsers() {
    return jdbcTemplate.query(MarketWiseSQL.GET_USERS, userMapper);
  }

  public User createUser(User user) {
    return jdbcTemplate.queryForObject(MarketWiseSQL.CREATE_USER,
        new Object[] {user.getUsername(), user.getEmail(), user.getPasswordHash()},
        (rs, rowNum) -> {
          user.setId(rs.getLong("id"));
          user.setCreatedAt(rs.getTimestamp("created_at").toInstant());
          return user;
        });
  }

  public void updateUser(User user) {
    jdbcTemplate.update(MarketWiseSQL.UPDATE_USER, user.getEmail(), user.getPasswordHash(), user.getId());
  }

  public void deleteUser(Long id) {
    jdbcTemplate.update(MarketWiseSQL.DELETE_USER, id);
  }
}
