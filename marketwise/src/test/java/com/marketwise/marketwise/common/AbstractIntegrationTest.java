package com.marketwise.marketwise.common;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
public abstract class AbstractIntegrationTest {

    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("marketwise_test")
            .withUsername("postgres")
            .withPassword("postgres");

    @BeforeAll
    public static void startContainer() {
        if (!postgresContainer.isRunning()) {
            postgresContainer.start();
        }
    }

    @AfterAll
    public static void stopContainer() {
        if (postgresContainer.isRunning()) {
            postgresContainer.stop();
        }
    }

    @DynamicPropertySource
    static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }
}
