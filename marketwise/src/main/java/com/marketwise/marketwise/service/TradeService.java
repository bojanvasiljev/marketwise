package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.Portfolio;
import com.marketwise.marketwise.model.PortfolioPosition;
import com.marketwise.marketwise.model.Trade;
import com.marketwise.marketwise.repository.PortfolioPositionRepository;
import com.marketwise.marketwise.repository.PortfolioRepository;
import com.marketwise.marketwise.repository.TradeRepository;
import com.marketwise.marketwise.validator.TradeValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TradeService {

  private final TradeRepository tradeRepository;
  private final PortfolioRepository portfolioRepository;
  private final PortfolioPositionRepository portfolioPositionRepository;

  private final TradeValidator tradeValidator;

  public TradeService(TradeRepository tradeRepository, PortfolioRepository portfolioRepository, PortfolioPositionRepository portfolioPositionRepository, TradeValidator tradeValidator) {
    this.tradeRepository = tradeRepository;
    this.portfolioRepository = portfolioRepository;
    this.portfolioPositionRepository = portfolioPositionRepository;
    this.tradeValidator = tradeValidator;
  }

  public List<Trade> getTradesByPortfolio(Long portfolioId) {
    return this.tradeRepository.getTradesByPortfolio(portfolioId);
  }

  @Transactional
  public Trade executeTrade(Trade trade) {
    Portfolio portfolio = this.portfolioRepository.getPortfolioById(trade.getPortfolioId());

    BigDecimal totalValue = trade.getPrice().multiply(trade.getShares());

    if ("BUY".equalsIgnoreCase(trade.getTradeType())) {

      this.tradeValidator.validateBuyTrade(portfolio, totalValue);

      this.portfolioRepository.updateCashBalance(portfolio.getId(), portfolio.getCashBalance().subtract(totalValue));

      // Update position
      PortfolioPosition portfolioPosition =
          this.portfolioPositionRepository.getPortfolioPositionsForPortfolio(portfolio.getId()).stream()
              .filter(p -> p.getStockSymbol().equals(trade.getStockSymbol()))
              .findFirst()
              .orElse(new PortfolioPosition(null, portfolio.getId(), trade.getStockSymbol(), BigDecimal.ZERO, BigDecimal.ZERO));

      BigDecimal newShares = portfolioPosition.getShares().add(trade.getShares());
      BigDecimal newAvgPrice = portfolioPosition.getShares().multiply(portfolioPosition.getAveragePrice()).add(totalValue).divide(newShares, 4, BigDecimal.ROUND_HALF_UP);

      portfolioPosition.setShares(newShares);
      portfolioPosition.setAveragePrice(newAvgPrice);
      this.portfolioPositionRepository.addOrUpdatePortfolioPosition(portfolioPosition);
    }
    else if ("SELL".equalsIgnoreCase(trade.getTradeType())) {

      PortfolioPosition portfolioPosition =
          this.portfolioPositionRepository.getPortfolioPositionsForPortfolio(portfolio.getId()).stream()
              .filter(p -> p.getStockSymbol().equals(trade.getStockSymbol()))
              .findFirst()
              .orElseThrow(() -> new IllegalArgumentException("No position to sell"));

      this.tradeValidator.validateSellTrade(portfolioPosition, trade);

      portfolioPosition.setShares(portfolioPosition.getShares().subtract(trade.getShares()));
      this.portfolioPositionRepository.addOrUpdatePortfolioPosition(portfolioPosition);
      this.portfolioRepository.updateCashBalance(portfolio.getId(), portfolio.getCashBalance().add(totalValue));
    }
    else {
      throw new IllegalArgumentException("Trade type must be BUY or SELL");
    }

    return this.tradeRepository.createTrade(trade);
  }
}