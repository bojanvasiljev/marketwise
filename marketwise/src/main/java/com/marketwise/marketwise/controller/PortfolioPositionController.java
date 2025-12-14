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
@RequestMapping("/api/positions")
@Tag(name = "Portfolio Positions", description = "Operations related to Portfolio Positions")
public class PortfolioPositionController {

    private final PortfolioPositionService service;

    public PortfolioPositionController(PortfolioPositionService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new portfolio position")
    @PostMapping
    public ResponseEntity<PortfolioPosition> createPosition(@RequestBody PortfolioPosition position) {
        return new ResponseEntity<>(service.createPosition(position), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all positions for a portfolio")
    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<List<PortfolioPosition>> getPositions(@PathVariable Long portfolioId) {
        return ResponseEntity.ok(service.getPositionsForPortfolio(portfolioId));
    }

    @Operation(summary = "Update a portfolio position")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePosition(@PathVariable Long id, @RequestBody PortfolioPosition position) {
        position.setId(id);
        service.updatePosition(position);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a portfolio position")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        service.deletePosition(id);
        return ResponseEntity.noContent().build();
    }
}
