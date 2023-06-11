package com.example.TestBackend;

import com.example.TestBackend.model.RaceResultView;

import com.example.TestBackend.service.RaceResultViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/race-results")
public class RaceResultViewController {
    private final RaceResultViewService raceResultViewService;

    @Autowired
    public RaceResultViewController(RaceResultViewService raceResultViewService) {
        this.raceResultViewService = raceResultViewService;
    }

    @GetMapping
    public ResponseEntity<List<RaceResultView>> getAllRaceResults() {
        List<RaceResultView> raceResults = raceResultViewService.getAllRaceResults();
        return new ResponseEntity<>(raceResults, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<RaceResultView>> getFilteredRaceResults(@RequestParam String category,
                                                                       @RequestParam int raceNumber) {
        List<RaceResultView> filteredResults = raceResultViewService.getFilteredRaceResults(category, raceNumber);
        return new ResponseEntity<>(filteredResults, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RaceResultView>> searchRaceResults(@RequestParam String keyword) {
        List<RaceResultView> searchResults = raceResultViewService.searchRaceResults(keyword);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }
}
