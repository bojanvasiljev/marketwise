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
      rs.getTimestamp("create_date").toInstant());

  public User getUserByUserId(Long userId) {
    return jdbcTemplate.queryForObject(MarketWiseSQL.GET_USER_BY_ID,
        new Object[] { userId },
        (rs, rowNum) -> {
          User user = new User();
          user.setId(rs.getLong("id"));
          user.setUsername(rs.getString("username"));
          user.setEmail(rs.getString("email"));
          user.setPasswordHash(rs.getString("password_hash"));
          user.setCreateDate(rs.getTimestamp("create_date").toInstant());
          return user;
        });
  }

  public List<User> getUsers() {
    return jdbcTemplate.query(MarketWiseSQL.GET_USERS, userMapper);
  }

  public User createUser(User user) {
    return jdbcTemplate.queryForObject(MarketWiseSQL.CREATE_USER,
        new Object[] { user.getUsername(), user.getEmail(), user.getPasswordHash() },
        (rs, rowNum) -> {
          user.setId(rs.getLong("id"));
          user.setCreateDate(rs.getTimestamp("create_date").toInstant());
          return user;
        });
  }

  public void updateUser(User user) {
    jdbcTemplate.update(MarketWiseSQL.UPDATE_USER, user.getEmail(), user.getPasswordHash(), user.getId());
  }

  public void deleteUser(Long userId) {
    jdbcTemplate.update(MarketWiseSQL.DELETE_USER, userId);
  }
}
