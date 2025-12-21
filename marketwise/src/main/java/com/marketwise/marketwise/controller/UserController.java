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

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Operation(summary = "Create a new user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input")
  })
  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody User user) {
    User created = this.userService.createUser(user);

    return new ResponseEntity<>(created, HttpStatus.CREATED);
  }

  @Operation(summary = "Get a user by ID")
  @GetMapping("/{userId}")
  public ResponseEntity<User> getUserById(@PathVariable Long userId) {
    return ResponseEntity.ok(this.userService.getUserByUserId(userId));
  }

  @Operation(summary = "Get all users")
  @GetMapping
  public ResponseEntity<List<User>> getUsers() {
    return ResponseEntity.ok(this.userService.getUsers());
  }

  @Operation(summary = "Update a user")
  @PutMapping("/{userId}")
  public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
    user.setId(userId);

    this.userService.updateUser(user);

    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Delete a user")
  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    this.userService.deleteUser(userId);

    return ResponseEntity.noContent().build();
  }
}
