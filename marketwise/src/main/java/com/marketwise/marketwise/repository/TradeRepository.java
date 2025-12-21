package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.common.MarketWiseSQL;
import com.marketwise.marketwise.model.Trade;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TradeRepository {
  
  private final JdbcTemplate jdbcTemplate;

  public TradeRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static final RowMapper<Trade> tradeRowMapper = (rs, rowNum) -> new Trade(
    rs.getLong("id"),
    rs.getLong("portfolio_id"),
    rs.getString("stock_symbol"),
    rs.getBigDecimal("shares"),
    rs.getBigDecimal("price"),
    rs.getString("trade_type"),
    rs.getTimestamp("create_date").toInstant()
  );

  public List<Trade> getTradesByPortfolio(Long portfolioId) {
    return this.jdbcTemplate.query(MarketWiseSQL.GET_TRADES_BY_PORTFOLIO, new Object[] { portfolioId }, tradeRowMapper);
  }

  public Trade createTrade(Trade trade) {
    return this.jdbcTemplate.queryForObject(MarketWiseSQL.CREATE_TRADE,
        new Object[] { trade.getPortfolioId(), trade.getStockSymbol(), trade.getShares(), trade.getPrice(), trade.getTradeType() },
        (rs, rowNum) -> {
          trade.setId(rs.getLong("id"));
          trade.setCreateDate(rs.getTimestamp("create_date").toInstant());
          return trade;
        });
  }
}
