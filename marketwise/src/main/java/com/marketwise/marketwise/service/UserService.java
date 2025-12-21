package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.User;
import com.marketwise.marketwise.repository.UserRepository;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(User user) {
    user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
    
    return this.userRepository.createUser(user);
  }

  public User getUserByUserId(Long userId) {
    return this.userRepository.getUserByUserId(userId);
  }

  public List<User> getUsers() {
    return this.userRepository.getUsers();
  }

  public void updateUser(User user) {
    user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
    
    this.userRepository.updateUser(user);
  }

  public void deleteUser(Long userId) {
    this.userRepository.deleteUser(userId);
  }
}
