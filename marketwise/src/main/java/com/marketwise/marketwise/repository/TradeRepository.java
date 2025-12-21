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
    Trade t = new Trade();
    t.setId(rs.getLong("id"));
    t.setPortfolioId(rs.getLong("portfolio_id"));
    t.setStockSymbol(rs.getString("stock_symbol"));
    t.setShares(rs.getBigDecimal("shares"));
    t.setPrice(rs.getBigDecimal("price"));
    t.setTradeType(rs.getString("trade_type"));
    t.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
    return t;
  };

  public List<Trade> getTradesByPortfolio(Long portfolioId) {
    return jdbcTemplate.query(MarketWiseSQL.GET_TRADES_BY_PORTFOLIO, new Object[] {portfolioId}, tradeMapper);
  }

  public Trade createTrade(Trade trade) {
    return jdbcTemplate.queryForObject(MarketWiseSQL.CREATE_TRADE,
        new Object[] {trade.getPortfolioId(), trade.getStockSymbol(), trade.getShares(),
            trade.getPrice(), trade.getTradeType()},
        (rs, rowNum) -> {
          trade.setId(rs.getLong("id"));
          trade.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
          return trade;
        });
  }
}
