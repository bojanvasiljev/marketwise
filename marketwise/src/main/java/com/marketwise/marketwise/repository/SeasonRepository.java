package com.marketwise.marketwise.repository;

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
            rs.getTimestamp("created_at").toInstant()
    );

    public Season getSeasonById(Long id) {
        String sql = "SELECT * FROM seasons WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Season s = new Season();
            s.setId(rs.getLong("id"));
            s.setName(rs.getString("name"));
            s.setStartDate(rs.getTimestamp("start_date").toInstant());
            s.setEndDate(rs.getTimestamp("end_date").toInstant());
            s.setCreatedAt(rs.getTimestamp("created_at").toInstant());
            return s;
        });
    }

    public List<Season> getAllSeasons() {
        String sql = "SELECT * FROM seasons ORDER BY start_date DESC";
        return jdbcTemplate.query(sql, seasonRowMapper);
    }

    public Season createSeason(Season season) {
        String sql = "INSERT INTO seasons (name, start_date, end_date, starting_cash) VALUES (?, ?, ?, ?) RETURNING id, created_at";
        return jdbcTemplate.queryForObject(sql,
                new Object[]{season.getName(), Timestamp.from(season.getStartDate()), Timestamp.from(season.getEndDate()), season.getStartingCash()},
                (rs, rowNum) -> {
                    season.setId(rs.getLong("id"));
                    season.setCreatedAt(rs.getTimestamp("created_at").toInstant());
                    return season;
                });
    }

    public int updateSeason(Season season) {
        String sql = "UPDATE seasons SET name = ?, start_date = ?, end_date = ? WHERE id = ?";
        return jdbcTemplate.update(sql, season.getName(), Timestamp.from(season.getStartDate()), Timestamp.from(season.getEndDate()), season.getId());
    }

    public void deleteSeason(Long id) {
        String sql = "DELETE FROM seasons WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
