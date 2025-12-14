package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AccountRepository {
    private final JdbcTemplate jdbcTemplate;

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Account> rowMapper = new RowMapper<>() {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account a = new Account();
            a.setId(rs.getLong("id"));
            a.setUserId(rs.getLong("user_id"));
            a.setSeasonId(rs.getLong("season_id"));
            a.setCashBalance(rs.getBigDecimal("cash_balance"));
            a.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return a;
        }
    };

    public Account createAccount(Account account) {
        String sql = "INSERT INTO accounts (user_id, season_id, cash_balance, created_at) " +
                     "VALUES (?, ?, ?, NOW()) RETURNING id, created_at";
        return jdbcTemplate.queryForObject(sql,
                new Object[]{account.getUserId(), account.getSeasonId(), account.getCashBalance()},
                (rs, rowNum) -> {
                    account.setId(rs.getLong("id"));
                    account.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    return account;
                });
    }

    public List<Account> getAccountsForSeason(Long seasonId) {
        String sql = "SELECT * FROM accounts WHERE season_id = ?";
        return jdbcTemplate.query(sql, new Object[]{seasonId}, rowMapper);
    }

    public Account getAccountByUserAndSeason(Long userId, Long seasonId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ? AND season_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, seasonId}, rowMapper);
    }

    public void updateCashBalance(Long accountId, java.math.BigDecimal newBalance) {
        String sql = "UPDATE accounts SET cash_balance = ? WHERE id = ?";
        jdbcTemplate.update(sql, newBalance, accountId);
    }
}
