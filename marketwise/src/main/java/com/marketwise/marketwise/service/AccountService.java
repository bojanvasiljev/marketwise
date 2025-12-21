package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.Account;
import com.marketwise.marketwise.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
  
  private final AccountRepository accountRepository;

  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public Account createAccount(Account account) {
    return this.accountRepository.createAccount(account);
  }

  public List<Account> getAccountsBySeason(Long seasonId) {
    return this.accountRepository.getAccountsBySeason(seasonId);
  }

  public Account getAccountForUserAndSeason(Long userId, Long seasonId) {
    return this.accountRepository.getAccountByUserAndSeason(userId, seasonId);
  }

  public void updateCashBalance(Long accountId, BigDecimal newBalance) {
    this.accountRepository.updateCashBalance(accountId, newBalance);
  }
}
