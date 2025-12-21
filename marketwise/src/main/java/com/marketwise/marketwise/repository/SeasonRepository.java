package com.marketwise.marketwise.repository;

import com.marketwise.marketwise.common.MarketWiseSQL;
import com.marketwise.marketwise.model.Season;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class SeasonRepository {

  private final JdbcTemplate jdbcTemplate;

  public SeasonRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  private static final RowMapper<Season> seasonRowMapper = (rs, rowNum) -> new Season(
      rs.getLong("id"),
      rs.getString("name"),
      rs.getTimestamp("start_date").toInstant(),
      rs.getTimestamp("end_date").toInstant(),
      rs.getBigDecimal("starting_cash"),
      rs.getTimestamp("created_at").toInstant());

  public Season getSeasonById(Long seasonId) {
    return jdbcTemplate.queryForObject(MarketWiseSQL.GET_SEASON_BY_ID, new Object[] {seasonId}, (rs, rowNum) -> {
      Season season = new Season();
      season.setId(rs.getLong("id"));
      season.setName(rs.getString("name"));
      season.setStartDate(rs.getTimestamp("start_date").toInstant());
      season.setEndDate(rs.getTimestamp("end_date").toInstant());
      season.setStartingCash(rs.getBigDecimal("starting_cash"));
      season.setCreatedAt(rs.getTimestamp("created_at").toInstant());
      return season;
    });
  }

  public List<Season> getAllSeasons() {
    return jdbcTemplate.query(MarketWiseSQL.GET_SEASONS, seasonRowMapper);
  }

  public Season createSeason(Season season) {
    return jdbcTemplate.queryForObject(MarketWiseSQL.CREATE_SEASON,
        new Object[] {season.getName(), Timestamp.from(season.getStartDate()), Timestamp.from(season.getEndDate()), season.getStartingCash()}, (rs, rowNum) -> {
          season.setId(rs.getLong("id"));
          season.setCreatedAt(rs.getTimestamp("created_at").toInstant());
          return season;
        });
  }

  public int updateSeason(Season season) {
    return jdbcTemplate.update(MarketWiseSQL.UPDATE_SEASON, season.getName(), Timestamp.from(season.getStartDate()), Timestamp.from(season.getEndDate()), season.getId());
  }

  public void deleteSeason(Long seasonId) {
    jdbcTemplate.update(MarketWiseSQL.DELETE_SEASON, seasonId);
  }
}
