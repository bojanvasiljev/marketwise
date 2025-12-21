package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.common.MarketWiseSQL;
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

  private final RowMapper<PortfolioPosition> portfolioPositionMapper = new RowMapper<>() {
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

  public List<PortfolioPosition> getPortfolioPositionsForPortfolio(Long portfolioId) {
    return jdbcTemplate.query(MarketWiseSQL.GET_PORTFOLIO_POSITIONS_BY_PORTFOLIO, new Object[] {portfolioId}, portfolioPositionMapper);
  }

  public PortfolioPosition createPortfolioPosition(PortfolioPosition portfolioPosition) {
    return jdbcTemplate.queryForObject(MarketWiseSQL.CREATE_PORTFOLIO_POSITION,
        new Object[] {portfolioPosition.getPortfolioId(), portfolioPosition.getStockSymbol(), portfolioPosition.getShares(), portfolioPosition.getAveragePrice()},
        (rs, rowNum) -> {
          portfolioPosition.setId(rs.getLong("id"));
          return portfolioPosition;
        });
  }

  public PortfolioPosition addOrUpdatePortfolioPosition(PortfolioPosition portfolioPosition) {
    // Check if position exists
    Integer count = jdbcTemplate.queryForObject(MarketWiseSQL.CHECK_PORTFOLIO_POSITION, new Object[] {portfolioPosition.getPortfolioId(), portfolioPosition.getStockSymbol()}, Integer.class);

    if (count != null && count > 0) { // Update existing
      jdbcTemplate.update(MarketWiseSQL.UPDATE_PORTFOLIO_POSITION, portfolioPosition.getShares(), portfolioPosition.getAveragePrice(), portfolioPosition.getPortfolioId(), portfolioPosition.getStockSymbol());
    }
    else { // Insert new
      Long id = jdbcTemplate.queryForObject(MarketWiseSQL.CREATE_PORTFOLIO_POSITION,
          new Object[] {portfolioPosition.getPortfolioId(), portfolioPosition.getStockSymbol(), portfolioPosition.getShares(), portfolioPosition.getAveragePrice()},
          Long.class);

      portfolioPosition.setId(id);
    }

    return portfolioPosition;
  }

  public void updatePortfolioPosition(PortfolioPosition portfolioPosition) {
    jdbcTemplate.update(MarketWiseSQL.UPDATE_PORTFOLIO_POSITION_SHARES, portfolioPosition.getShares(), portfolioPosition.getAveragePrice(), portfolioPosition.getId());
  }

  public void deletePortfolioPosition(Long portfolioPositionId) {
    jdbcTemplate.update(MarketWiseSQL.DELETE_PORTFOLIO_POSITION, portfolioPositionId);
  }
}
