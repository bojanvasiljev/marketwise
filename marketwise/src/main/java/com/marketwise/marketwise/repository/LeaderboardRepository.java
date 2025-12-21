package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.common.MarketWiseSQL;
import com.marketwise.marketwise.model.UserLeaderboard;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LeaderboardRepository {

  private final JdbcTemplate jdbcTemplate;

  public LeaderboardRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<UserLeaderboard> getSeasonLeaderboard(Long seasonId, int limit) {
    return this.jdbcTemplate.query(MarketWiseSQL.GET_LEADERBOARD_BY_SEASON,
        new Object[] {seasonId, limit},
        (rs, rowNum) -> new UserLeaderboard(
            rs.getLong("user_id"),
            rs.getString("username"),
            rs.getBigDecimal("net_worth")));
  }
}
