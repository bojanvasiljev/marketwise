package com.marketwise.marketwise.controller;

import com.marketwise.marketwise.model.Season;
import com.marketwise.marketwise.service.SeasonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seasons")
@Tag(name = "Seasons", description = "Operations related to Seasons")
public class SeasonController {

  private final SeasonService seasonService;

  public SeasonController(SeasonService seasonService) {
    this.seasonService = seasonService;
  }

  @Operation(summary = "Get season by ID")
  @GetMapping("/{seasonId}")
  public ResponseEntity<Season> getSeasonById(@PathVariable Long seasonId) {
    return ResponseEntity.ok(seasonService.getSeasonById(seasonId));
  }

  @Operation(summary = "Get all seasons")
  @GetMapping
  public ResponseEntity<List<Season>> getAllSeasons() {
    return ResponseEntity.ok(seasonService.getAllSeasons());
  }

  @Operation(summary = "Create a new season")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Season created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input")
  })
  @PostMapping
  public ResponseEntity<Season> createSeason(@RequestBody Season season) {
    return new ResponseEntity<>(seasonService.createSeason(season), HttpStatus.CREATED);
  }

  @Operation(summary = "Update an existing season")
  @PutMapping("/{seasonId}")
  public ResponseEntity<Season> updateSeason(@PathVariable Long seasonId, @RequestBody Season season) {
    season.setId(seasonId);
    return ResponseEntity.ok(seasonService.updateSeason(season));
  }

  @Operation(summary = "Delete a season")
  @DeleteMapping("/{seasonId}")
  public ResponseEntity<Void> deleteSeason(@PathVariable Long seasonId) {
    seasonService.deleteSeason(seasonId);
    return ResponseEntity.noContent().build();
  }
}