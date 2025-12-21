package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.Season;
import com.marketwise.marketwise.repository.SeasonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class SeasonService {

  private final SeasonRepository seasonRepository;

  public SeasonService(SeasonRepository seasonRepository) {
    this.seasonRepository = seasonRepository;
  }

  public Season getSeasonById(Long seasonId) {
    return seasonRepository.getSeasonById(seasonId);
  }

  public List<Season> getAllSeasons() {
    return seasonRepository.getAllSeasons();
  }

  @Transactional
  public Season createSeason(Season season) {
    this.validateDates(season.getStartDate(), season.getEndDate());
    this.validateStartingCash(season.getStartingCash());
    this.ensureNoOverlap(season.getStartDate(), season.getEndDate());

    return seasonRepository.createSeason(season);
  }

  public Season updateSeason(Season season) {
    seasonRepository.updateSeason(season);
    return seasonRepository.getSeasonById(season.getId());
  }

  public void deleteSeason(Long id) {
    seasonRepository.deleteSeason(id);
  }

  private void validateDates(Instant start, Instant end) {
    if (start == null || end == null) {
      throw new IllegalArgumentException("Start and end date are required");
    }
    if (!start.isBefore(end)) {
      throw new IllegalArgumentException("Start date must be before end date");
    }
  }

  private void validateStartingCash(BigDecimal startingCash) {
    if (startingCash == null) {
      throw new IllegalArgumentException("Starting cash is required");
    }
    if (startingCash.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Starting cash must be positive");
    }
  }

  private void ensureNoOverlap(Instant start, Instant end) {
    seasonRepository.getAllSeasons().forEach(existing -> {

      boolean overlaps = start.isBefore(existing.getEndDate()) && end.isAfter(existing.getStartDate());
      if (overlaps) {
        throw new IllegalStateException("Season dates overlap with existing season: " + existing.getName());
      }
    });
  }
}