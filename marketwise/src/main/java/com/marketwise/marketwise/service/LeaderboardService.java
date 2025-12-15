package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.UserLeaderboard;
import com.marketwise.marketwise.repository.LeaderboardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderboardService {

    private final LeaderboardRepository repository;

    public LeaderboardService(LeaderboardRepository repository) {
        this.repository = repository;
    }

    public List<UserLeaderboard> getSeasonLeaderboard(Long seasonId, int limit) {
        return repository.getSeasonLeaderboard(seasonId, limit);
    }
}