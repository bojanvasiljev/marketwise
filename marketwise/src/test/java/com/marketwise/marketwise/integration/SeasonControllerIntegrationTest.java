package com.marketwise.marketwise.integration;

import com.marketwise.marketwise.MarketwiseApplication;
import com.marketwise.marketwise.common.AbstractIntegrationTest;
import com.marketwise.marketwise.model.Season;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for SeasonController using Testcontainers PostgreSQL instance.
 */
@SpringBootTest(
        classes = MarketwiseApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeasonControllerIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String baseUrl() {
        return "http://localhost:" + port + "/api/seasons";
    }

    @BeforeEach
    void cleanDb() {
        jdbcTemplate.execute("TRUNCATE TABLE seasons RESTART IDENTITY CASCADE");
    }

    @Test
    @Order(1)
    void testGetEmptySeasons() {
        ResponseEntity<Season[]> response = restTemplate.getForEntity(baseUrl(), Season[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
    }

    @Test
    @Order(2)
    void testCreateSeason() {
        Season season = new Season(
                null,
                "Integration Season",
                Instant.parse("2025-01-01T00:00:00Z"),
                Instant.parse("2025-02-01T00:00:00Z"),
                new BigDecimal("100000"),
                null
        );

        ResponseEntity<Season> response = restTemplate.postForEntity(baseUrl(), season, Season.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Season createdSeason = response.getBody();
        assertThat(createdSeason).isNotNull();
        assertThat(createdSeason.getName()).isEqualTo("Integration Season");
        assertThat(createdSeason.getId()).isNotNull();
    }

    @Test
    @Order(3)
    void testGetSeasonsAfterCreation() {
        Season season = new Season(
                null,
                "Integration Season",
                Instant.parse("2025-01-01T00:00:00Z"),
                Instant.parse("2025-02-01T00:00:00Z"),
                new BigDecimal("100000"),
                null
        );
        restTemplate.postForEntity(baseUrl(), season, Season.class);

        ResponseEntity<Season[]> response = restTemplate.getForEntity(baseUrl(), Season[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Season[] seasons = response.getBody();
        assertThat(seasons).isNotEmpty();
        assertThat(seasons[0].getName()).isEqualTo("Integration Season");
    }

    @Test
    @Order(4)
    void testUpdateSeason() {
        // Create season first
        Season season = new Season(
                null,
                "Old Season",
                Instant.parse("2025-01-01T00:00:00Z"),
                Instant.parse("2025-02-01T00:00:00Z"),
                new BigDecimal("100000"),
                null
        );
        Season created = restTemplate.postForEntity(baseUrl(), season, Season.class).getBody();

        // Update name
        created.setName("Updated Season");
        HttpEntity<Season> request = new HttpEntity<>(created);
        ResponseEntity<Season> response = restTemplate.exchange(baseUrl() + "/" + created.getId(), HttpMethod.PUT, request, Season.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Season updated = response.getBody();
        assertThat(updated).isNotNull();
        assertThat(updated.getName()).isEqualTo("Updated Season");
    }

    @Test
    @Order(5)
    void testDeleteSeason() {
        // Create season first
        Season season = new Season(
                null,
                "Season To Delete",
                Instant.parse("2025-01-01T00:00:00Z"),
                Instant.parse("2025-02-01T00:00:00Z"),
                new BigDecimal("100000"),
                null
        );
        Season created = restTemplate.postForEntity(baseUrl(), season, Season.class).getBody();

        restTemplate.delete(baseUrl() + "/" + created.getId());

        ResponseEntity<Season[]> response = restTemplate.getForEntity(baseUrl(), Season[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Season[] seasons = response.getBody();
        assertThat(seasons).doesNotContain(created);
    }
}
