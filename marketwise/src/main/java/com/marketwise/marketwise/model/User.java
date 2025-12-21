package com.marketwise.marketwise.model;

import java.time.Instant;

public class User {

  private Long id;
  private String username;
  private String email;
  private String passwordHash;
  private Instant createDate;

  public User() {}

  public User(Long id, String username, String email, String passwordHash, Instant createDate) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.passwordHash = passwordHash;
    this.createDate = createDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public Instant getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Instant createDate) {
    this.createDate = createDate;
  }
}
