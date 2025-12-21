package com.marketwise.marketwise.model;

import java.math.BigDecimal;
import java.time.Instant;

public class PortfolioPosition {

  private Long id;
  private Long portfolioId;
  private String stockSymbol;
  private BigDecimal shares;
  private BigDecimal averagePrice;
  private Instant createDate;

  public PortfolioPosition() {}

  public PortfolioPosition(Long id, Long portfolioId, String stockSymbol, BigDecimal shares, BigDecimal averagePrice) {
    this.id = id;
    this.portfolioId = portfolioId;
    this.stockSymbol = stockSymbol;
    this.shares = shares;
    this.averagePrice = averagePrice;
  }

  public PortfolioPosition(Long id, Long portfolioId, String stockSymbol, BigDecimal shares, BigDecimal averagePrice, Instant createDate) {
    this.id = id;
    this.portfolioId = portfolioId;
    this.stockSymbol = stockSymbol;
    this.shares = shares;
    this.averagePrice = averagePrice;
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

  public BigDecimal getAveragePrice() {
    return averagePrice;
  }

  public void setAveragePrice(BigDecimal averagePrice) {
    this.averagePrice = averagePrice;
  }

  public Instant getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Instant createDate) {
    this.createDate = createDate;
  }
}
