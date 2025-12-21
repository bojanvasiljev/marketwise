package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.UserLeaderboard;
import com.marketwise.marketwise.repository.LeaderboardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderboardService {

  private final LeaderboardRepository leaderboardRepository;

  public LeaderboardService(LeaderboardRepository leaderboardRepository) {
      this.leaderboardRepository = leaderboardRepository;
  }

  public List<UserLeaderboard> getSeasonLeaderboard(Long seasonId, int limit) {
    return leaderboardRepository.getSeasonLeaderboard(seasonId, limit);
  }
}
