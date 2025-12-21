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

  private static final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
      rs.getLong("id"),
      rs.getString("username"),
      rs.getString("email"),
      rs.getString("password_hash"),
      rs.getTimestamp("create_date").toInstant()
  );

  public User createUser(User user) {
    return this.jdbcTemplate.queryForObject(MarketWiseSQL.CREATE_USER,
        new Object[] { user.getUsername(), user.getEmail(), user.getPasswordHash() },
        (rs, rowNum) -> {
          user.setId(rs.getLong("id"));
          user.setCreateDate(rs.getTimestamp("create_date").toInstant());
          return user;
        });
  }

  public User getUserByUserId(Long userId) {
    return this.jdbcTemplate.queryForObject(MarketWiseSQL.GET_USER_BY_ID, new Object[] { userId }, userRowMapper);
  }

  public List<User> getUsers() {
    return this.jdbcTemplate.query(MarketWiseSQL.GET_USERS, userRowMapper);
  }

  public void updateUser(User user) {
    this.jdbcTemplate.update(MarketWiseSQL.UPDATE_USER, user.getEmail(), user.getPasswordHash(), user.getId());
  }

  public void deleteUser(Long userId) {
    this.jdbcTemplate.update(MarketWiseSQL.DELETE_USER, userId);
  }
}
