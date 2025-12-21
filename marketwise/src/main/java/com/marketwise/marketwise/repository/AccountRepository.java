package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.common.MarketWiseSQL;
import com.marketwise.marketwise.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepository {

  private final JdbcTemplate jdbcTemplate;

  public AccountRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static final RowMapper<Account> accountRowMapper = (rs, rowNum) -> new Account(
    rs.getLong("id"),
    rs.getLong("user_id"),
    rs.getLong("season_id"),
    rs.getBigDecimal("cash_balance"),
    rs.getTimestamp("create_date").toInstant()
  );

  public Account createAccount(Account account) {
    return this.jdbcTemplate.queryForObject(MarketWiseSQL.CREATE_ACCOUNT,
        new Object[] { account.getUserId(), account.getSeasonId(), account.getCashBalance() },
        (rs, rowNum) -> {
          account.setId(rs.getLong("id"));
          account.setCreateDate(rs.getTimestamp("create_date").toInstant());
          return account;
        });
  }

  public List<Account> getAccountsBySeason(Long seasonId) {
    return this.jdbcTemplate.query(MarketWiseSQL.GET_ACCOUNTS_BY_SEASON, new Object[] { seasonId }, accountRowMapper);
  }

  public Account getAccountByUserAndSeason(Long userId, Long seasonId) {
    return this.jdbcTemplate.queryForObject(MarketWiseSQL.GET_ACCOUNT_BY_USER_AND_SEASON, new Object[] { userId, seasonId }, accountRowMapper);
  }

  public void updateCashBalance(Long accountId, java.math.BigDecimal newBalance) {
    this.jdbcTemplate.update(MarketWiseSQL.UPDATE_CASH_BALANCE, newBalance, accountId);
  }
}
