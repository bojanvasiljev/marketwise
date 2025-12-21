package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.Account;
import com.marketwise.marketwise.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
  
  private final AccountRepository repository;

  public AccountService(AccountRepository repository) {
    this.repository = repository;
  }

  public List<Account> getAccountsBySeason(Long seasonId) {
    return repository.getAccountsBySeason(seasonId);
  }

  public Account getAccountForUserAndSeason(Long userId, Long seasonId) {
    return repository.getAccountByUserAndSeason(userId, seasonId);
  }

  public Account createAccount(Account account) {
    return repository.createAccount(account);
  }

  public void updateCashBalance(Long accountId, BigDecimal newBalance) {
    repository.updateCashBalance(accountId, newBalance);
  }
}
