package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.common.MarketWiseSQL;
import com.marketwise.marketwise.model.PortfolioPosition;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PortfolioPositionRepository {

  private final JdbcTemplate jdbcTemplate;

  public PortfolioPositionRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static final RowMapper<PortfolioPosition> portfolioPositionRowMapper = (rs, rowNum) -> new PortfolioPosition(
    rs.getLong("id"),
    rs.getLong("portfolio_id"),
    rs.getString("stock_symbol"),
    rs.getBigDecimal("shares"),
    rs.getBigDecimal("average_price"),
    rs.getTimestamp("create_date").toInstant()
  );

  public List<PortfolioPosition> getPortfolioPositionsForPortfolio(Long portfolioId) {
    return this.jdbcTemplate.query(MarketWiseSQL.GET_PORTFOLIO_POSITIONS_BY_PORTFOLIO, new Object[] { portfolioId }, portfolioPositionRowMapper);
  }

  public PortfolioPosition createPortfolioPosition(PortfolioPosition portfolioPosition) {
    return this.jdbcTemplate.queryForObject(MarketWiseSQL.CREATE_PORTFOLIO_POSITION,
        new Object[] { portfolioPosition.getPortfolioId(), portfolioPosition.getStockSymbol(), portfolioPosition.getShares(), portfolioPosition.getAveragePrice() },
        (rs, rowNum) -> {
          portfolioPosition.setId(rs.getLong("id"));
          return portfolioPosition;
        });
  }

  public PortfolioPosition addOrUpdatePortfolioPosition(PortfolioPosition portfolioPosition) {
    // Check if position exists
    Integer count = this.jdbcTemplate.queryForObject(MarketWiseSQL.CHECK_PORTFOLIO_POSITION, new Object[] { portfolioPosition.getPortfolioId(), portfolioPosition.getStockSymbol() }, Integer.class);

    if (count != null && count > 0) { // Update existing
      this.jdbcTemplate.update(MarketWiseSQL.UPDATE_PORTFOLIO_POSITION, portfolioPosition.getShares(), portfolioPosition.getAveragePrice(), portfolioPosition.getPortfolioId(), portfolioPosition.getStockSymbol());
    }
    else { // Insert new
      Long id = this.jdbcTemplate.queryForObject(MarketWiseSQL.CREATE_PORTFOLIO_POSITION,
          new Object[] { portfolioPosition.getPortfolioId(), portfolioPosition.getStockSymbol(), portfolioPosition.getShares(), portfolioPosition.getAveragePrice() },
          Long.class);

      portfolioPosition.setId(id);
    }

    return portfolioPosition;
  }

  public void updatePortfolioPosition(PortfolioPosition portfolioPosition) {
    this.jdbcTemplate.update(MarketWiseSQL.UPDATE_PORTFOLIO_POSITION_SHARES, portfolioPosition.getShares(), portfolioPosition.getAveragePrice(), portfolioPosition.getId());
  }

  public void deletePortfolioPosition(Long portfolioPositionId) {
    this.jdbcTemplate.update(MarketWiseSQL.DELETE_PORTFOLIO_POSITION, portfolioPositionId);
  }
}
