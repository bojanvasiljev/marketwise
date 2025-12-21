package com.marketwise.marketwise.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Portfolio {

  private Long id;
  private Long userId;
  private BigDecimal cashBalance;
  private Instant createDate;

  // Constructors
  public Portfolio() {}

  public Portfolio(Long id, Long userId, BigDecimal cashBalance, Instant createDate) {
    this.id = id;
    this.userId = userId;
    this.cashBalance = cashBalance;
    this.createDate = createDate;
  }

  // Getters & Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public BigDecimal getCashBalance() {
    return cashBalance;
  }

  public void setCashBalance(BigDecimal cashBalance) {
    this.cashBalance = cashBalance;
  }

  public Instant getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Instant createDate) {
    this.createDate = createDate;
  }
}
