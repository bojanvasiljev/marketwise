package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.Portfolio;
import com.marketwise.marketwise.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PortfolioService {

  private final PortfolioRepository portfolioRepository;

  public PortfolioService(PortfolioRepository portfolioRepository) {
    this.portfolioRepository = portfolioRepository;
  }

  public Portfolio getPortfolioByUser(Long userId) {
    return this.portfolioRepository.getPortfolioByUser(userId);
  }

  public Portfolio createPortfolio(Portfolio portfolio) {
    return this.portfolioRepository.createPortfolio(portfolio);
  }

  public void updateCashBalance(Long portfolioId, BigDecimal newBalance) {
    this.portfolioRepository.updateCashBalance(portfolioId, newBalance);
  }
}
