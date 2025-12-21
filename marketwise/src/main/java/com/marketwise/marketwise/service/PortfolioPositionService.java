package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.PortfolioPosition;
import com.marketwise.marketwise.repository.PortfolioPositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioPositionService {

  private final PortfolioPositionRepository portfolioPositionRepository;

  public PortfolioPositionService(PortfolioPositionRepository portfolioPositionRepository) {
    this.portfolioPositionRepository = portfolioPositionRepository;
  }

  public List<PortfolioPosition> getPortfolioPositionsForPortfolio(Long portfolioId) {
    return this.portfolioPositionRepository.getPortfolioPositionsForPortfolio(portfolioId);
  }

  public PortfolioPosition createPortfolioPosition(PortfolioPosition portfolioPosition) {
    return this.portfolioPositionRepository.createPortfolioPosition(portfolioPosition);
  }

  public PortfolioPosition addOrUpdatePortfolioPosition(PortfolioPosition portfolioPosition) {
    return this.portfolioPositionRepository.addOrUpdatePortfolioPosition(portfolioPosition);
  }

  public void updatePortfolioPosition(PortfolioPosition portfolioPosition) {
    this.portfolioPositionRepository.updatePortfolioPosition(portfolioPosition);
  }

  public void deletePortfolioPosition(Long portfolioPositionId) {
    this.portfolioPositionRepository.deletePortfolioPosition(portfolioPositionId);
  }
}
