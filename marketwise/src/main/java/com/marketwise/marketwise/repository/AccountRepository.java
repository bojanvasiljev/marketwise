package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.common.MarketWiseSQL;
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
      Account account = new Account();
      account.setId(rs.getLong("id"));
      account.setUserId(rs.getLong("user_id"));
      account.setSeasonId(rs.getLong("season_id"));
      account.setCashBalance(rs.getBigDecimal("cash_balance"));
      account.setCreateDate(rs.getTimestamp("create_date").toInstant());
      return account;
    }
  };

  public List<Account> getAccountsBySeason(Long seasonId) {
    return jdbcTemplate.query(MarketWiseSQL.GET_ACCOUNTS_BY_SEASON, new Object[] { seasonId }, rowMapper);
  }

  public Account getAccountByUserAndSeason(Long userId, Long seasonId) {
    return jdbcTemplate.queryForObject(MarketWiseSQL.GET_ACCOUNT_BY_USER_AND_SEASON, new Object[] { userId, seasonId }, rowMapper);
  }

  public Account createAccount(Account account) {
    return jdbcTemplate.queryForObject(MarketWiseSQL.CREATE_ACCOUNT,
        new Object[] { account.getUserId(), account.getSeasonId(), account.getCashBalance() },
        (rs, rowNum) -> {
          account.setId(rs.getLong("id"));
          account.setCreateDate(rs.getTimestamp("create_date").toInstant());
          return account;
        });
  }

  public void updateCashBalance(Long accountId, java.math.BigDecimal newBalance) {
    jdbcTemplate.update(MarketWiseSQL.UPDATE_CASH_BALANCE, newBalance, accountId);
  }
}