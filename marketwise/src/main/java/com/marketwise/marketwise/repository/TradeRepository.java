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

  private final RowMapper<Trade> tradeMapper = (rs, rowNum) -> {
    Trade trade = new Trade();
    trade.setId(rs.getLong("id"));
    trade.setPortfolioId(rs.getLong("portfolio_id"));
    trade.setStockSymbol(rs.getString("stock_symbol"));
    trade.setShares(rs.getBigDecimal("shares"));
    trade.setPrice(rs.getBigDecimal("price"));
    trade.setTradeType(rs.getString("trade_type"));
    trade.setCreateDate(rs.getTimestamp("create_date").toInstant());
    return trade;
  };

  public List<Trade> getTradesByPortfolio(Long portfolioId) {
    return jdbcTemplate.query(MarketWiseSQL.GET_TRADES_BY_PORTFOLIO, new Object[] { portfolioId }, tradeMapper);
  }

  public Trade createTrade(Trade trade) {
    return jdbcTemplate.queryForObject(MarketWiseSQL.CREATE_TRADE,
        new Object[] { trade.getPortfolioId(), trade.getStockSymbol(), trade.getShares(), trade.getPrice(), trade.getTradeType() },
        (rs, rowNum) -> {
          trade.setId(rs.getLong("id"));
          trade.setCreateDate(rs.getTimestamp("create_date").toInstant());
          return trade;
        });
  }
}
