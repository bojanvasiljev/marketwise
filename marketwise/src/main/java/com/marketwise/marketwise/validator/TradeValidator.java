package com.marketwise.marketwise.validator;

import org.springframework.stereotype.Component;
import com.marketwise.marketwise.model.Trade;
import com.marketwise.marketwise.model.Portfolio;
import com.marketwise.marketwise.model.PortfolioPosition;

import java.math.BigDecimal;

@Component
public class TradeValidator {

  public void validateBuyTrade(Portfolio portfolio, BigDecimal totalValue) {
    // Check cash balance
    if (portfolio.getCashBalance().compareTo(totalValue) < 0) {
      throw new IllegalArgumentException("Insufficient cash balance");
    }
  }

  public void validateSellTrade(PortfolioPosition portfolioPosition, Trade trade) {
    if (portfolioPosition.getShares().compareTo(trade.getShares()) < 0) {
      throw new IllegalArgumentException("Not enough shares to sell");
    }
  }
}
