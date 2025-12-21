package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.Portfolio;
import com.marketwise.marketwise.model.PortfolioPosition;
import com.marketwise.marketwise.model.Trade;
import com.marketwise.marketwise.repository.PortfolioPositionRepository;
import com.marketwise.marketwise.repository.PortfolioRepository;
import com.marketwise.marketwise.repository.TradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TradeService {

  private final TradeRepository tradeRepository;
  private final PortfolioRepository portfolioRepository;
  private final PortfolioPositionRepository portfolioPositionRepository;

  public TradeService(TradeRepository tradeRepository, PortfolioRepository portfolioRepository, PortfolioPositionRepository portfolioPositionRepository) {
    this.tradeRepository = tradeRepository;
    this.portfolioRepository = portfolioRepository;
    this.portfolioPositionRepository = portfolioPositionRepository;
  }

  public List<Trade> getTradesByPortfolio(Long portfolioId) {
    return tradeRepository.getTradesByPortfolio(portfolioId);
  }

  @Transactional
  public Trade executeTrade(Trade trade) {
    Portfolio portfolio = portfolioRepository.getPortfolioById(trade.getPortfolioId());

    BigDecimal totalValue = trade.getPrice().multiply(trade.getShares());

    if ("BUY".equalsIgnoreCase(trade.getTradeType())) {
      // Check cash balance
      if (portfolio.getCashBalance().compareTo(totalValue) < 0) {
        throw new IllegalArgumentException("Insufficient cash balance");
      }

      portfolioRepository.updateCashBalance(portfolio.getId(), portfolio.getCashBalance().subtract(totalValue));

      // Update position
      PortfolioPosition portfolioPosition =
          portfolioPositionRepository.getPortfolioPositionsForPortfolio(portfolio.getId()).stream()
              .filter(p -> p.getStockSymbol().equals(trade.getStockSymbol()))
              .findFirst()
              .orElse(new PortfolioPosition(null, portfolio.getId(), trade.getStockSymbol(),
                  BigDecimal.ZERO, BigDecimal.ZERO));

      BigDecimal newShares = portfolioPosition.getShares().add(trade.getShares());
      BigDecimal newAvgPrice = portfolioPosition.getShares().multiply(portfolioPosition.getAveragePrice()).add(totalValue).divide(newShares, 4, BigDecimal.ROUND_HALF_UP);

      portfolioPosition.setShares(newShares);
      portfolioPosition.setAveragePrice(newAvgPrice);
      portfolioPositionRepository.addOrUpdatePortfolioPosition(portfolioPosition);
    }
    else if ("SELL".equalsIgnoreCase(trade.getTradeType())) {

      PortfolioPosition portfolioPosition =
          portfolioPositionRepository.getPortfolioPositionsForPortfolio(portfolio.getId()).stream()
              .filter(p -> p.getStockSymbol().equals(trade.getStockSymbol()))
              .findFirst()
              .orElseThrow(() -> new IllegalArgumentException("No position to sell"));

      if (portfolioPosition.getShares().compareTo(trade.getShares()) < 0) {
        throw new IllegalArgumentException("Not enough shares to sell");
      }

      portfolioPosition.setShares(portfolioPosition.getShares().subtract(trade.getShares()));
      portfolioPositionRepository.addOrUpdatePortfolioPosition(portfolioPosition);
      portfolioRepository.updateCashBalance(portfolio.getId(), portfolio.getCashBalance().add(totalValue));
    }
    else {
      throw new IllegalArgumentException("Trade type must be BUY or SELL");
    }

    return tradeRepository.createTrade(trade);
  }
}