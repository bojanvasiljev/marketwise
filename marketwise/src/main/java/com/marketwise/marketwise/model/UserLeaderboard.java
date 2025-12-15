package com.marketwise.marketwise.model;

import java.math.BigDecimal;

public class UserLeaderboard {
  
    private Long userId;
    private String username;
    private BigDecimal netWorth;

    public UserLeaderboard(Long userId, String username, BigDecimal netWorth) {
        this.userId = userId;
        this.username = username;
        this.netWorth = netWorth;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public BigDecimal getNetWorth() {
        return netWorth;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNetWorth(BigDecimal netWorth) {
        this.netWorth = netWorth;
    }
}