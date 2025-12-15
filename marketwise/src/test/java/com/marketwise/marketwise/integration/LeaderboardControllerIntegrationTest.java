package com.marketwise.marketwise.integration;

import com.marketwise.marketwise.MarketwiseApplication;
import com.marketwise.marketwise.model.UserLeaderboard;
import com.marketwise.marketwise.common.AbstractIntegrationTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = MarketwiseApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LeaderboardControllerIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/leaderboard";
    }

    @BeforeEach
    void cleanDb() {
        jdbcTemplate.execute("TRUNCATE TABLE users, accounts, portfolio_positions RESTART IDENTITY CASCADE");
    }

    @Test
    @Order(1)
    void testSeasonLeaderboard() {
        // Seed users
        jdbcTemplate.update("INSERT INTO users (username, email, password_hash) VALUES ('user1','u1@example.com','hash1')");
        jdbcTemplate.update("INSERT INTO users (username, email, password_hash) VALUES ('user2','u2@example.com','hash2')");

        // Seed accounts and positions
        jdbcTemplate.update("INSERT INTO accounts (user_id, season_id, cash_balance) VALUES (1, 1, 1000)");
        jdbcTemplate.update("INSERT INTO accounts (user_id, season_id, cash_balance) VALUES (2, 1, 500)");

        jdbcTemplate.update("INSERT INTO portfolio_positions (portfolio_id, stock_symbol, shares, average_price) VALUES (1,'AAPL',5,100)");
        jdbcTemplate.update("INSERT INTO portfolio_positions (portfolio_id, stock_symbol, shares, average_price) VALUES (2,'AAPL',10,20)");

        ResponseEntity<UserLeaderboard[]> response =
                restTemplate.getForEntity(baseUrl() + "/season/1?limit=10", UserLeaderboard[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserLeaderboard[] leaderboard = response.getBody();
        assertThat(leaderboard).isNotNull();
        assertThat(leaderboard.length).isEqualTo(2);

        // Check ordering by net worth
        assertThat(leaderboard[0].getUserId()).isEqualTo(1L); // user1 should have higher net worth
        assertThat(leaderboard[1].getUserId()).isEqualTo(2L);
    }
}
