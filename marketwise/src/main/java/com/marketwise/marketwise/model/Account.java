package com.marketwise.marketwise.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

public class Account {
  
  private Long id;
  private Long userId;
  private Long seasonId;
  private BigDecimal cashBalance;
  private Instant createDate;

  public Account() {}

  public Account(Long id, Long userId, Long seasonId, BigDecimal cashBalance, Instant createDate) {
    this.id = id;
    this.userId = userId;
    this.seasonId = seasonId;
    this.cashBalance = cashBalance;
    this.createDate = createDate;
  }

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

  public Long getSeasonId() {
    return seasonId;
  }

  public void setSeasonId(Long seasonId) {
    this.seasonId = seasonId;
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
