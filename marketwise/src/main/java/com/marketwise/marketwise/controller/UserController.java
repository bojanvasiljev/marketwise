package com.marketwise.marketwise.controller;

import com.marketwise.marketwise.model.User;
import com.marketwise.marketwise.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Operations related to Users")
public class UserController {

  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @Operation(summary = "Get a user by ID")
  @GetMapping("/{userId}")
  public ResponseEntity<User> getUserById(@PathVariable Long userId) {
    return ResponseEntity.ok(service.getUserById(userId));
  }

  @Operation(summary = "Get all users")
  @GetMapping
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok(service.getUsers());
  }

  @Operation(summary = "Create a new user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input")
  })
  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    User created = service.createUser(user);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  @Operation(summary = "Update a user")
  @PutMapping("/{userId}")
  public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
    user.setId(userId);
    service.updateUser(user);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Delete a user")
  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    service.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}