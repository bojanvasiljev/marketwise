package com.marketwise.marketwise.controller;

import com.marketwise.marketwise.model.UserLeaderboard;
import com.marketwise.marketwise.service.LeaderboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@Tag(name = "Leaderboards", description = "Operations related to Leaderboards")
public class LeaderboardController {

  private final LeaderboardService leaderboardService;

  public LeaderboardController(LeaderboardService leaderboardService) {
    this.leaderboardService = leaderboardService;
  }

  @Operation(summary = "Get leader board for a season")
  @GetMapping("/season/{seasonId}")
  public ResponseEntity<List<UserLeaderboard>> getSeasonLeaderboard(@PathVariable Long seasonId, @RequestParam(defaultValue = "10") int limit) {
    return ResponseEntity.ok(leaderboardService.getSeasonLeaderboard(seasonId, limit));
  }
}
