package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.PortfolioPosition;
import com.marketwise.marketwise.repository.PortfolioPositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioPositionService {
    private final PortfolioPositionRepository repository;

    public PortfolioPositionService(PortfolioPositionRepository repository) {
        this.repository = repository;
    }

    public PortfolioPosition createPosition(PortfolioPosition position) {
        return repository.createPosition(position);
    }

    public List<PortfolioPosition> getPositionsForPortfolio(Long portfolioId) {
        return repository.getPositionsByPortfolioId(portfolioId);
    }

    public void updatePosition(PortfolioPosition position) {
        repository.updatePosition(position);
    }

    public void deletePosition(Long id) {
        repository.deletePosition(id);
    }
}
