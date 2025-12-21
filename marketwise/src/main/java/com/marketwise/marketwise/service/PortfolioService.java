package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.Portfolio;
import com.marketwise.marketwise.model.PortfolioPosition;
import com.marketwise.marketwise.repository.PortfolioPositionRepository;
import com.marketwise.marketwise.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PortfolioService {

  private final PortfolioRepository repository;
  private final PortfolioPositionRepository portfolioPositionRepository;

  public PortfolioService(PortfolioRepository repository, PortfolioPositionRepository portfolioPositionRepository) {
    this.repository = repository;
    this.portfolioPositionRepository = portfolioPositionRepository;
  }

  public Portfolio getPortfolioByUser(Long userId) {
    return repository.getPortfolioByUser(userId);
  }

  public Portfolio createPortfolio(Portfolio portfolio) {
    return repository.createPortfolio(portfolio);
  }

  public void updateCashBalance(Long portfolioId, BigDecimal newBalance) {
    repository.updateCashBalance(portfolioId, newBalance);
  }
}
