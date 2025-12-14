// package com.marketwise.marketwise.config;

// import com.marketwise.marketwise.repository.SeasonRepository;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import java.math.BigDecimal;
// import java.time.Instant;

// @Configuration
// public class SeasonSmokeTestRunner {

//     @Bean
//     CommandLineRunner seasonSmokeTest(SeasonRepository seasonRepository) {
//         return args -> {

//             if (seasonRepository.findAll().isEmpty()) {
//                 seasonRepository.insert("Season 1", Instant.now(), Instant.now().plusSeconds(60L * 60 * 24 * 30), new BigDecimal("100000.0000"));
//             }

//             seasonRepository.findAll()
//                     .forEach(season ->
//                             System.out.println("Loaded season: " + season.getName())
//                     );
//         };
//     }
// }
