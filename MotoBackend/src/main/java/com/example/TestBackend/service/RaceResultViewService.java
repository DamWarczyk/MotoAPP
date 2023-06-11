package com.example.TestBackend.service;

import com.example.TestBackend.model.RaceResultView;

import com.example.TestBackend.repo.RaceResultViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceResultViewService {
    private final RaceResultViewRepository raceResultViewRepository;

    @Autowired
    public RaceResultViewService(RaceResultViewRepository raceResultViewRepository) {
        this.raceResultViewRepository = raceResultViewRepository;
    }

    public List<RaceResultView> getAllRaceResults() {
        return raceResultViewRepository.findAll();
    }

    public List<RaceResultView> getFilteredRaceResults(String category, int raceNumber) {
        // filtrowania wyników na podstawie kategorii i numeru wyścigu
        return raceResultViewRepository.findByCategoryAndRaceNumber(category, raceNumber);
    }

    public List<RaceResultView> searchRaceResults(String keyword) {
        // wyszukiwania wyników na podstawie słowa kluczowego
        return raceResultViewRepository.findByRiderNameContainingIgnoreCase(keyword);
    }
}
