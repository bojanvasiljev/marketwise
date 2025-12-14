package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.model.Trade;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public Trade createTrade(Trade trade) {
        String sql = "INSERT INTO trades (portfolio_id, stock_symbol, shares, price, trade_type, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, NOW()) RETURNING id, created_at";
        return jdbcTemplate.queryForObject(sql,
                new Object[]{trade.getPortfolioId(), trade.getStockSymbol(), trade.getShares(), trade.getPrice(), trade.getTradeType()},
                (rs, rowNum) -> {
                    trade.setId(rs.getLong("id"));
                    trade.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    return trade;
                });
    }

    public List<Trade> getTradesByPortfolio(Long portfolioId) {
        String sql = "SELECT * FROM trades WHERE portfolio_id = ?";
        return jdbcTemplate.query(sql, new Object[]{portfolioId}, tradeMapper);
    }
}
