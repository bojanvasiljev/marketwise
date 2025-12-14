package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.model.PortfolioPosition;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PortfolioPositionRepository {
    private final JdbcTemplate jdbcTemplate;

    public PortfolioPositionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<PortfolioPosition> rowMapper = new RowMapper<>() {
        @Override
        public PortfolioPosition mapRow(ResultSet rs, int rowNum) throws SQLException {
            PortfolioPosition p = new PortfolioPosition();
            p.setId(rs.getLong("id"));
            p.setPortfolioId(rs.getLong("portfolio_id"));
            p.setStockSymbol(rs.getString("stock_symbol"));
            p.setShares(rs.getBigDecimal("shares"));
            p.setAveragePrice(rs.getBigDecimal("average_price"));
            return p;
        }
    };

    public PortfolioPosition createPosition(PortfolioPosition position) {
        String sql = "INSERT INTO portfolio_positions (portfolio_id, stock_symbol, shares, average_price) VALUES (?, ?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql,
                new Object[]{position.getPortfolioId(), position.getStockSymbol(), position.getShares(), position.getAveragePrice()},
                (rs, rowNum) -> {
                    position.setId(rs.getLong("id"));
                    return position;
                });
    }

    public List<PortfolioPosition> getPositionsByPortfolioId(Long portfolioId) {
        String sql = "SELECT * FROM portfolio_positions WHERE portfolio_id = ?";
        return jdbcTemplate.query(sql, new Object[]{portfolioId}, rowMapper);
    }

    public void updatePosition(PortfolioPosition position) {
        String sql = "UPDATE portfolio_positions SET shares = ?, average_price = ? WHERE id = ?";
        jdbcTemplate.update(sql, position.getShares(), position.getAveragePrice(), position.getId());
    }

    public void deletePosition(Long id) {
        String sql = "DELETE FROM portfolio_positions WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
