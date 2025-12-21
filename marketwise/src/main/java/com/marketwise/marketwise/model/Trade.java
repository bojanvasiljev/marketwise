package com.marketwise.marketwise.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Trade {

  private Long id;
  private Long portfolioId;
  private String stockSymbol;
  private BigDecimal shares;
  private BigDecimal price;
  private String tradeType; // BUY or SELL
  private Instant createDate;

  public Trade() {}

  public Trade(Long id, Long portfolioId, String stockSymbol, BigDecimal shares, BigDecimal price, String tradeType, Instant createDate) {
    this.id = id;
    this.portfolioId = portfolioId;
    this.stockSymbol = stockSymbol;
    this.shares = shares;
    this.price = price;
    this.tradeType = tradeType;
    this.createDate = createDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(Long portfolioId) {
    this.portfolioId = portfolioId;
  }

  public String getStockSymbol() {
    return stockSymbol;
  }

  public void setStockSymbol(String stockSymbol) {
    this.stockSymbol = stockSymbol;
  }

  public BigDecimal getShares() {
    return shares;
  }

  public void setShares(BigDecimal shares) {
    this.shares = shares;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getTradeType() {
    return tradeType;
  }

  public void setTradeType(String tradeType) {
    this.tradeType = tradeType;
  }

  public Instant getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Instant createDate) {
    this.createDate = createDate;
  }
}
