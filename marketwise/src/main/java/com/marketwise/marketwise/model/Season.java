package com.marketwise.marketwise.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Season {

    private Long id;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private BigDecimal startingCash;
    private Instant createdAt;

    public Season() {}

    public Season(Long id, String name, Instant startDate, Instant endDate, BigDecimal startingCash, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startingCash = startingCash;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Instant getStartDate() {
        return startDate;
    }
    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }
    public Instant getEndDate() {
        return endDate;
    }
    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }
    public BigDecimal getStartingCash() {
        return startingCash;
    }
    public void setStartingCash(BigDecimal startingCash) {
        this.startingCash = startingCash;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

