package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.Portfolio;
import com.marketwise.marketwise.model.PortfolioPosition;
import com.marketwise.marketwise.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PortfolioService {

  private final PortfolioRepository repository;

  public PortfolioService(PortfolioRepository repository) {
    this.repository = repository;
  }

  public Portfolio createPortfolio(Portfolio portfolio) {
    return repository.createPortfolio(portfolio);
  }

  public Portfolio getPortfolioByUser(Long userId) {
    return repository.getPortfolioByUser(userId);
  }

  public void updateCashBalance(Long portfolioId, BigDecimal newBalance) {
    repository.updateCashBalance(portfolioId, newBalance);
  }

  public PortfolioPosition addOrUpdatePosition(PortfolioPosition position) {
    return repository.addOrUpdatePosition(position);
  }

  public List<PortfolioPosition> getPositions(Long portfolioId) {
    return repository.getPositions(portfolioId);
  }
}
