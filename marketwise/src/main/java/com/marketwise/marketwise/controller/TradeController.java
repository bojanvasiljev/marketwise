package com.marketwise.marketwise.controller;

import com.marketwise.marketwise.model.Trade;
import com.marketwise.marketwise.service.TradeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
@Tag(name = "Trades", description = "Operations related to Trades")
public class TradeController {
  
  private final TradeService service;

  public TradeController(TradeService service) {
    this.service = service;
  }

  @Operation(summary = "Get all trades for a portfolio")
  @GetMapping("/portfolio/{portfolioId}")
  public ResponseEntity<List<Trade>> getTrades(@PathVariable Long portfolioId) {
    return ResponseEntity.ok(service.getTradesByPortfolio(portfolioId));
  }

  @Operation(summary = "Create a new trade")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Trade created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input")
  })
  @PostMapping
  public ResponseEntity<Trade> executeTrade(@RequestBody Trade trade) {
    return new ResponseEntity<>(service.executeTrade(trade), HttpStatus.CREATED);
  }
}