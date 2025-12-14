package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.model.Portfolio;
import com.marketwise.marketwise.model.PortfolioPosition;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

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
        p.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return p;
    };

    private final RowMapper<PortfolioPosition> positionMapper = (rs, rowNum) -> {
        PortfolioPosition pos = new PortfolioPosition();
        pos.setId(rs.getLong("id"));
        pos.setPortfolioId(rs.getLong("portfolio_id"));
        pos.setStockSymbol(rs.getString("stock_symbol"));
        pos.setShares(rs.getBigDecimal("shares"));
        pos.setAveragePrice(rs.getBigDecimal("average_price"));
        return pos;
    };

    // ---- Portfolio CRUD ----
    public Portfolio createPortfolio(Portfolio portfolio) {
        String sql;
        Object[] params;

        if (portfolio.getCashBalance() != null) {
            sql = "INSERT INTO portfolios (user_id, cash_balance, created_at) VALUES (?, ?, NOW()) RETURNING id, created_at, cash_balance";
            params = new Object[]{portfolio.getUserId(), portfolio.getCashBalance()};
        } else {
            sql = "INSERT INTO portfolios (user_id, created_at) VALUES (?, NOW()) RETURNING id, created_at, cash_balance";
            params = new Object[]{portfolio.getUserId()};
        }

        return jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
            portfolio.setId(rs.getLong("id"));
            portfolio.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            portfolio.setCashBalance(rs.getBigDecimal("cash_balance"));
            return portfolio;
        });
    }

    public Portfolio getPortfolioByUser(Long userId) {
        String sql = "SELECT * FROM portfolios WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId}, portfolioMapper);
    }

    public Portfolio getPortfolioById(Long portfolioId) {
      String sql = "SELECT * FROM portfolios WHERE id = ?";
      
      return jdbcTemplate.queryForObject(sql, new Object[]{portfolioId}, (rs, rowNum) -> {
          Portfolio p = new Portfolio();
          p.setId(rs.getLong("id"));
          p.setUserId(rs.getLong("user_id"));
          p.setCashBalance(rs.getBigDecimal("cash_balance"));
          p.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
          return p;
      });
    }

    public void updateCashBalance(Long portfolioId, BigDecimal newBalance) {
        String sql = "UPDATE portfolios SET cash_balance = ? WHERE id = ?";
        jdbcTemplate.update(sql, newBalance, portfolioId);
    }

    // ---- PortfolioPosition CRUD ----
    public PortfolioPosition addOrUpdatePosition(PortfolioPosition position) {
        // Check if position exists
        String checkSql = "SELECT COUNT(*) FROM portfolio_positions WHERE portfolio_id = ? AND stock_symbol = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, new Object[]{position.getPortfolioId(), position.getStockSymbol()}, Integer.class);

        if (count != null && count > 0) {
            // Update existing
            String updateSql = "UPDATE portfolio_positions SET shares = ?, average_price = ? WHERE portfolio_id = ? AND stock_symbol = ?";
            jdbcTemplate.update(updateSql, position.getShares(), position.getAveragePrice(), position.getPortfolioId(), position.getStockSymbol());
        } else {
            // Insert new
            String insertSql = "INSERT INTO portfolio_positions (portfolio_id, stock_symbol, shares, average_price) " +
                               "VALUES (?, ?, ?, ?) RETURNING id";
            Long id = jdbcTemplate.queryForObject(insertSql,
                    new Object[]{position.getPortfolioId(), position.getStockSymbol(), position.getShares(), position.getAveragePrice()},
                    Long.class);
            position.setId(id);
        }

        return position;
    }

    public List<PortfolioPosition> getPositions(Long portfolioId) {
        String sql = "SELECT * FROM portfolio_positions WHERE portfolio_id = ?";
        return jdbcTemplate.query(sql, new Object[]{portfolioId}, positionMapper);
    }
}
