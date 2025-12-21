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
      rs.getTimestamp("create_date").toInstant()
  );

  public Season getSeasonById(Long seasonId) {
    return this.jdbcTemplate.queryForObject(MarketWiseSQL.GET_SEASON_BY_ID, new Object[] { seasonId }, seasonRowMapper);
  }

  public List<Season> getAllSeasons() {
    return this.jdbcTemplate.query(MarketWiseSQL.GET_SEASONS, seasonRowMapper);
  }

  public Season createSeason(Season season) {
    return this.jdbcTemplate.queryForObject(MarketWiseSQL.CREATE_SEASON,
        new Object[] { season.getName(), Timestamp.from(season.getStartDate()), Timestamp.from(season.getEndDate()), season.getStartingCash() }, (rs, rowNum) -> {
          season.setId(rs.getLong("id"));
          season.setCreateDate(rs.getTimestamp("create_date").toInstant());
          return season;
        });
  }

  public int updateSeason(Season season) {
    return this.jdbcTemplate.update(MarketWiseSQL.UPDATE_SEASON, season.getName(), Timestamp.from(season.getStartDate()), Timestamp.from(season.getEndDate()), season.getId());
  }

  public void deleteSeason(Long seasonId) {
    this.jdbcTemplate.update(MarketWiseSQL.DELETE_SEASON, seasonId);
  }
}
