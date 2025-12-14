package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.Portfolio;
import com.marketwise.marketwise.model.PortfolioPosition;
import com.marketwise.marketwise.model.Trade;
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

    public TradeService(TradeRepository tradeRepository, PortfolioRepository portfolioRepository) {
        this.tradeRepository = tradeRepository;
        this.portfolioRepository = portfolioRepository;
    }

    @Transactional
    public Trade executeTrade(Trade trade) {
        Portfolio portfolio = portfolioRepository.getPortfolioById(trade.getPortfolioId());

        BigDecimal totalValue = trade.getPrice().multiply(trade.getShares());

        if ("BUY".equalsIgnoreCase(trade.getTradeType())) {
            if (portfolio.getCashBalance().compareTo(totalValue) < 0) {
                throw new IllegalArgumentException("Insufficient cash balance");
            }
            portfolioRepository.updateCashBalance(portfolio.getId(), portfolio.getCashBalance().subtract(totalValue));
            // Update position
            PortfolioPosition position = portfolioRepository.getPositions(portfolio.getId()).stream()
                    .filter(p -> p.getStockSymbol().equals(trade.getStockSymbol()))
                    .findFirst()
                    .orElse(new PortfolioPosition(null, portfolio.getId(), trade.getStockSymbol(), BigDecimal.ZERO, BigDecimal.ZERO));

            BigDecimal newShares = position.getShares().add(trade.getShares());
            BigDecimal newAvgPrice = position.getShares().multiply(position.getAveragePrice())
                    .add(totalValue).divide(newShares, 4, BigDecimal.ROUND_HALF_UP);

            position.setShares(newShares);
            position.setAveragePrice(newAvgPrice);
            portfolioRepository.addOrUpdatePosition(position);

        } else if ("SELL".equalsIgnoreCase(trade.getTradeType())) {
            PortfolioPosition position = portfolioRepository.getPositions(portfolio.getId()).stream()
                    .filter(p -> p.getStockSymbol().equals(trade.getStockSymbol()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No position to sell"));

            if (position.getShares().compareTo(trade.getShares()) < 0) {
                throw new IllegalArgumentException("Not enough shares to sell");
            }

            position.setShares(position.getShares().subtract(trade.getShares()));
            portfolioRepository.addOrUpdatePosition(position);
            portfolioRepository.updateCashBalance(portfolio.getId(), portfolio.getCashBalance().add(totalValue));
        } else {
            throw new IllegalArgumentException("Trade type must be BUY or SELL");
        }

        return tradeRepository.createTrade(trade);
    }

    public List<Trade> getTradesByPortfolio(Long portfolioId) {
        return tradeRepository.getTradesByPortfolio(portfolioId);
    }
}
