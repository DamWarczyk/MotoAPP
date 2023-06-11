package com.example.TestBackend.repo;

import com.example.TestBackend.model.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
    List<Rider> findAllByOrderByNumberAsc(); // Sortowanie po numerze rosnąco

    List<Rider> findAllByOrderByNumberDesc(); // Sortowanie po numerze malejąco

    List<Rider> findAllByTeam(String team); // Filtrowanie po nazwie zespołu
// w razie czego
    Rider findById(long id);

    Optional<Rider> findByTeamAndAndNumber(String team, Integer number);
}
