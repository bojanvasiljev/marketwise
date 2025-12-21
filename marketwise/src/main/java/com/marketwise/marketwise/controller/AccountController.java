package com.marketwise.marketwise.controller;

import com.marketwise.marketwise.model.Account;
import com.marketwise.marketwise.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "Operations related to Accounts")
public class AccountController {

  private final AccountService service;

  public AccountController(AccountService service) {
    this.service = service;
  }

  @Operation(summary = "Get all accounts by season")
  @GetMapping("/season/{seasonId}")
  public ResponseEntity<List<Account>> getAccountsBySeason(@PathVariable Long seasonId) {
    return ResponseEntity.ok(service.getAccountsBySeason(seasonId));
  }

  @Operation(summary = "Get all account by user and season")
  @GetMapping("/user/{userId}/season/{seasonId}")
  public ResponseEntity<Account> getAccountForUserAndSeason(@PathVariable Long userId, @PathVariable Long seasonId) {
    return ResponseEntity.ok(service.getAccountForUserAndSeason(userId, seasonId));
  }

  @Operation(summary = "Create a new account for a user and season")
  @PostMapping
  public ResponseEntity<Account> createAccount(@RequestBody Account account) {
    return new ResponseEntity<>(service.createAccount(account), HttpStatus.CREATED);
  }

  @Operation(summary = "Update cash balance for an account")
  @PutMapping("/{accountId}/balance")
  public ResponseEntity<Void> updateCashBalance(@PathVariable Long accountId, @RequestBody BigDecimal newBalance) {
    service.updateCashBalance(accountId, newBalance);
    return ResponseEntity.noContent().build();
  }
}
