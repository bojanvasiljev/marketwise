package com.marketwise.marketwise.validator;

import com.marketwise.marketwise.model.Season;
import com.marketwise.marketwise.repository.SeasonRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Component
public class SeasonValidator {

  private final SeasonRepository seasonRepository;

  public SeasonValidator(SeasonRepository seasonRepository) {
    this.seasonRepository = seasonRepository;
  }

  public void validateSeason(Season season) {
    this.validateDates(season.getStartDate(), season.getEndDate());
    this.validateStartingCash(season.getStartingCash());
    this.ensureNoOverlap(season.getStartDate(), season.getEndDate());
  }

  private void validateDates(Instant startDate, Instant endDate) {
    if (startDate == null || endDate == null) {
      throw new IllegalArgumentException("Start and end date are required");
    }
    if (startDate.isBefore(endDate) == false) {
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

  private void ensureNoOverlap(Instant startDate, Instant endDate) {
    List<Season> allSeasons = seasonRepository.getAllSeasons();

    for (Season existing : allSeasons) {

      boolean overlaps = startDate.isBefore(existing.getEndDate()) && endDate.isAfter(existing.getStartDate());
      if (overlaps) {
        throw new IllegalStateException("Season dates overlap with existing season: " + existing.getName());
      }
    }
  }
}
