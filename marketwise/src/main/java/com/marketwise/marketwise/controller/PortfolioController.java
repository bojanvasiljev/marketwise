package com.marketwise.marketwise.controller;

import com.marketwise.marketwise.model.Portfolio;
import com.marketwise.marketwise.service.PortfolioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/portfolios")
@Tag(name = "Portfolios", description = "Operations related to Portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @Operation(summary = "Get portfolio for a user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Portfolio> getPortfolioByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(portfolioService.getPortfolioByUser(userId));
    }

    @Operation(summary = "Create a new portfolio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Portfolio created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Portfolio> createPortfolio(@RequestBody Portfolio portfolio) {
        return new ResponseEntity<>(portfolioService.createPortfolio(portfolio), HttpStatus.CREATED);
    }

    @Operation(summary = "Update cash balance for a portfolio")
    @PutMapping("/{portfolioId}/balance")
    public ResponseEntity<Void> updateCashBalance(@PathVariable Long portfolioId, @RequestBody BigDecimal newBalance) {
        portfolioService.updateCashBalance(portfolioId, newBalance);
        return ResponseEntity.noContent().build();
    }
}