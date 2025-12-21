package com.marketwise.marketwise.service;

import com.marketwise.marketwise.model.Season;
import com.marketwise.marketwise.repository.SeasonRepository;
import com.marketwise.marketwise.validator.SeasonValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeasonService {

  private final SeasonRepository seasonRepository;
  private final SeasonValidator seasonValidator;

  public SeasonService(SeasonRepository seasonRepository, SeasonValidator seasonValidator) {
    this.seasonRepository = seasonRepository;
    this.seasonValidator = seasonValidator;
  }

  public Season getSeasonById(Long seasonId) {
    return seasonRepository.getSeasonById(seasonId);
  }

  public List<Season> getAllSeasons() {
    return seasonRepository.getAllSeasons();
  }

  @Transactional
  public Season createSeason(Season season) {
    this.seasonValidator.validateSeason(season);

    return seasonRepository.createSeason(season);
  }

  public Season updateSeason(Season season) {
    this.seasonRepository.updateSeason(season);
    return this.seasonRepository.getSeasonById(season.getId());
  }

  public void deleteSeason(Long seasonId) {
    this.seasonRepository.deleteSeason(seasonId);
  }
}
