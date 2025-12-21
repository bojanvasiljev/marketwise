package com.marketwise.marketwise.controller;

import com.marketwise.marketwise.model.PortfolioPosition;
import com.marketwise.marketwise.service.PortfolioPositionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio/positions")
@Tag(name = "Portfolio Positions", description = "Operations related to Portfolio Positions")
public class PortfolioPositionController {

    private final PortfolioPositionService portfolioPositionService;

    public PortfolioPositionController(PortfolioPositionService portfolioPositionService) {
        this.portfolioPositionService = portfolioPositionService;
    }

    @Operation(summary = "Get all positions for a portfolio")
    @GetMapping("/{portfolioId}")
    public ResponseEntity<List<PortfolioPosition>> getPortfolioPositionsForPortfolio(@PathVariable Long portfolioId) {
        return ResponseEntity.ok(portfolioPositionService.getPortfolioPositionsForPortfolio(portfolioId));
    }

    @Operation(summary = "Create a new portfolio position")
    @PostMapping
    public ResponseEntity<PortfolioPosition> createPortfolioPosition(@RequestBody PortfolioPosition portfolioPosition) {
        return new ResponseEntity<>(portfolioPositionService.createPortfolioPosition(portfolioPosition), HttpStatus.CREATED);
    }

    @Operation(summary = "Add or update a portfolio position")
    @PostMapping("/{portfolioId}")
    public ResponseEntity<PortfolioPosition> addOrUpdatePortfolioPosition(@PathVariable Long portfolioId, @RequestBody PortfolioPosition portfolioPosition) {
        portfolioPosition.setPortfolioId(portfolioId);
        return new ResponseEntity<>(portfolioPositionService.addOrUpdatePortfolioPosition(portfolioPosition), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a portfolio position")
    @PutMapping("/{portfolioPositionId}")
    public ResponseEntity<Void> updatePortfolioPosition(@PathVariable Long portfolioPositionId, @RequestBody PortfolioPosition portfolioPosition) {
        portfolioPosition.setId(portfolioPositionId);
        portfolioPositionService.updatePortfolioPosition(portfolioPosition);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a portfolio position")
    @DeleteMapping("/{portfolioPositionId}")
    public ResponseEntity<Void> deletePortfolioPosition(@PathVariable Long portfolioPositionId) {
        portfolioPositionService.deletePortfolioPosition(portfolioPositionId);
        return ResponseEntity.noContent().build();
    }
}
