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
    return portfolioPositionRepository.getPortfolioPositionsForPortfolio(portfolioId);
  }

  public PortfolioPosition createPortfolioPosition(PortfolioPosition portfolioPosition) {
    return portfolioPositionRepository.createPortfolioPosition(portfolioPosition);
  }

  public PortfolioPosition addOrUpdatePortfolioPosition(PortfolioPosition portfolioPosition) {
    return portfolioPositionRepository.addOrUpdatePortfolioPosition(portfolioPosition);
  }

  public void updatePortfolioPosition(PortfolioPosition portfolioPosition) {
    portfolioPositionRepository.updatePortfolioPosition(portfolioPosition);
  }

  public void deletePortfolioPosition(Long portfolioPositionId) {
    portfolioPositionRepository.deletePortfolioPosition(portfolioPositionId);
  }
}
