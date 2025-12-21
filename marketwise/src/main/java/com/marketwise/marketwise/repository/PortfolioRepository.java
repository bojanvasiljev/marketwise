package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.common.MarketWiseSQL;
import com.marketwise.marketwise.model.Portfolio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class PortfolioRepository {

  private final JdbcTemplate jdbcTemplate;

  public PortfolioRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static final RowMapper<Portfolio> portfolioRowMapper = (rs, rowNum) -> new Portfolio(
    rs.getLong("id"),
    rs.getLong("user_id"),
    rs.getBigDecimal("cash_balance"),
    rs.getTimestamp("create_date").toInstant()
  );

  public Portfolio createPortfolio(Portfolio portfolio) {
    String sql;
    Object[] params;

    if (portfolio.getCashBalance() != null) {
      sql = MarketWiseSQL.CREATE_PORTFOLIO;
      params = new Object[] { portfolio.getUserId(), portfolio.getCashBalance() };
    }
    else {
      sql = MarketWiseSQL.CREATE_PORTFOLIO_NO_CASH;
      params = new Object[] { portfolio.getUserId() };
    }

    return this.jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
      portfolio.setId(rs.getLong("id"));
      portfolio.setCreateDate(rs.getTimestamp("create_date").toInstant());
      portfolio.setCashBalance(rs.getBigDecimal("cash_balance"));
      return portfolio;
    });
  }

  public Portfolio getPortfolioByUser(Long userId) {
    return this.jdbcTemplate.queryForObject(MarketWiseSQL.GET_PORTFOLIO_BY_USER, new Object[] { userId }, portfolioRowMapper);
  }

  public Portfolio getPortfolioById(Long portfolioId) {
    return this.jdbcTemplate.queryForObject(MarketWiseSQL.GET_PORTFOLIO_BY_PORTFOLIO, new Object[] { portfolioId }, portfolioRowMapper);
  }

  public void updateCashBalance(Long portfolioId, BigDecimal newBalance) {
    this.jdbcTemplate.update(MarketWiseSQL.UPDATE_PORTFOLIO_CASH_BALANCE, newBalance, portfolioId);
  }
}
