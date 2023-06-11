package com.example.TestBackend.repo;

import com.example.TestBackend.model.RaceResultView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RaceResultViewRepository extends JpaRepository<RaceResultView, Long> {

    List<RaceResultView> findByCategoryAndRaceNumber(String category, int raceNumber);

    List<RaceResultView> findByRiderNameContainingIgnoreCase(String keyword);
}
