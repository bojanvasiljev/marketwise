package com.marketwise.marketwise.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Trade {
    private Long id;
    private Long portfolioId;
    private String stockSymbol;
    private BigDecimal shares;
    private BigDecimal price;
    private String tradeType; // BUY or SELL
    private LocalDateTime createdAt;

    public Trade() {}

    public Trade(Long id, Long portfolioId, String stockSymbol, BigDecimal shares, BigDecimal price, String tradeType, LocalDateTime createdAt) {
        this.id = id;
        this.portfolioId = portfolioId;
        this.stockSymbol = stockSymbol;
        this.shares = shares;
        this.price = price;
        this.tradeType = tradeType;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPortfolioId() { return portfolioId; }
    public void setPortfolioId(Long portfolioId) { this.portfolioId = portfolioId; }
    public String getStockSymbol() { return stockSymbol; }
    public void setStockSymbol(String stockSymbol) { this.stockSymbol = stockSymbol; }
    public BigDecimal getShares() { return shares; }
    public void setShares(BigDecimal shares) { this.shares = shares; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getTradeType() { return tradeType; }
    public void setTradeType(String tradeType) { this.tradeType = tradeType; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
