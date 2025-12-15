package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.model.UserLeaderboard;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class LeaderboardRepository {

    private final JdbcTemplate jdbcTemplate;

    public LeaderboardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserLeaderboard> getSeasonLeaderboard(Long seasonId, int limit) {
        String sql = """
            "SELECT u.id AS user_id, u.username, 
                   SUM(a.cash_balance + COALESCE(p.total_value, 0)) AS net_worth
            FROM users u
            JOIN accounts a ON a.user_id = u.id
            LEFT JOIN (
                SELECT portfolio_id, SUM(shares * average_price) AS total_value
                FROM portfolio_positions
                GROUP BY portfolio_id
            ) p ON p.portfolio_id = a.id
            WHERE a.season_id = ?
            GROUP BY u.id, u.username
            ORDER BY net_worth DESC
            LIMIT ?
            """;

        return jdbcTemplate.query(
                sql,
                new Object[]{seasonId, limit},
                (rs, rowNum) -> new UserLeaderboard(
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getBigDecimal("net_worth")
                )
        );
    }
}