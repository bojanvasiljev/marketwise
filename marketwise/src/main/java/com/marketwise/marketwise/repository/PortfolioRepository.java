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

  private final RowMapper<Portfolio> portfolioMapper = (rs, rowNum) -> {
    Portfolio p = new Portfolio();
    p.setId(rs.getLong("id"));
    p.setUserId(rs.getLong("user_id"));
    p.setCashBalance(rs.getBigDecimal("cash_balance"));
    p.setCreateDate(rs.getTimestamp("create_date").toInstant());
    return p;
  };

  public Portfolio getPortfolioByUser(Long userId) {
    return jdbcTemplate.queryForObject(MarketWiseSQL.GET_PORTFOLIO_BY_USER, new Object[] { userId }, portfolioMapper);
  }

  public Portfolio getPortfolioById(Long portfolioId) {
    return jdbcTemplate.queryForObject(MarketWiseSQL.GET_PORTFOLIO_BY_PORTFOLIO, new Object[] { portfolioId }, (rs, rowNum) -> {
      Portfolio portfolio = new Portfolio();
      portfolio.setId(rs.getLong("id"));
      portfolio.setUserId(rs.getLong("user_id"));
      portfolio.setCashBalance(rs.getBigDecimal("cash_balance"));
      portfolio.setCreateDate(rs.getTimestamp("create_date").toInstant());
      return portfolio;
    });
  }

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

    return jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
      portfolio.setId(rs.getLong("id"));
      portfolio.setCreateDate(rs.getTimestamp("create_date").toInstant());
      portfolio.setCashBalance(rs.getBigDecimal("cash_balance"));
      return portfolio;
    });
  }

  public void updateCashBalance(Long portfolioId, BigDecimal newBalance) {
    jdbcTemplate.update(MarketWiseSQL.UPDATE_PORTFOLIO_CASH_BALANCE, newBalance, portfolioId);
  }
}
