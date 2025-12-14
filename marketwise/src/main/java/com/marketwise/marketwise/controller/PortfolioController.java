package com.marketwise.marketwise.controller;

import com.marketwise.marketwise.model.Portfolio;
import com.marketwise.marketwise.model.PortfolioPosition;
import com.marketwise.marketwise.service.PortfolioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
@Tag(name = "Portfolios", description = "Operations related to Portfolios")
public class PortfolioController {

    private final PortfolioService service;

    public PortfolioController(PortfolioService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new portfolio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Portfolio created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Portfolio> createPortfolio(@RequestBody Portfolio portfolio) {
        return new ResponseEntity<>(service.createPortfolio(portfolio), HttpStatus.CREATED);
    }

    @Operation(summary = "Get portfolio for a user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<Portfolio> getPortfolioByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getPortfolioByUser(userId));
    }

    @Operation(summary = "Update cash balance for a portfolio")
    @PutMapping("/{portfolioId}/balance")
    public ResponseEntity<Void> updateCashBalance(@PathVariable Long portfolioId, @RequestBody BigDecimal newBalance) {
        service.updateCashBalance(portfolioId, newBalance);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add or update a portfolio position")
    @PostMapping("/{portfolioId}/positions")
    public ResponseEntity<PortfolioPosition> addOrUpdatePosition(@PathVariable Long portfolioId, @RequestBody PortfolioPosition position) {
        position.setPortfolioId(portfolioId);
        return new ResponseEntity<>(service.addOrUpdatePosition(position), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all positions in a portfolio")
    @GetMapping("/{portfolioId}/positions")
    public ResponseEntity<List<PortfolioPosition>> getPositions(@PathVariable Long portfolioId) {
        return ResponseEntity.ok(service.getPositions(portfolioId));
    }
}