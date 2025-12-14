package com.marketwise.marketwise.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Portfolio {
    private Long id;
    private Long userId;
    private BigDecimal cashBalance;
    private LocalDateTime createdAt;

    // Constructors
    public Portfolio() {}
    public Portfolio(Long id, Long userId, BigDecimal cashBalance, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.cashBalance = cashBalance;
        this.createdAt = createdAt;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public BigDecimal getCashBalance() { return cashBalance; }
    public void setCashBalance(BigDecimal cashBalance) { this.cashBalance = cashBalance; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
