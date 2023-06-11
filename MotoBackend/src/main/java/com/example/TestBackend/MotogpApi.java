package com.example.TestBackend;

import com.github.parsad23.motogpapi.domain.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MotogpApi {
    private List<RiderStandings> riderStandings;
    private List<TeamStandings> teamStandings;
    private List<ConstructorStandings> constructorStandings;

    public MotogpApi(List<RiderStandings> riderStandings, List<TeamStandings> teamStandings, List<ConstructorStandings> constructorStandings) {
        this.riderStandings = riderStandings;
        this.teamStandings = teamStandings;
        this.constructorStandings = constructorStandings;
    }

    public List<RiderStandings> getRiderStandings() {
        return riderStandings;
    }

    public List<TeamStandings> getTeamStandings() {
        return teamStandings;
    }

    public List<ConstructorStandings> getConstructorStandings() {
        return constructorStandings;
    }

//    public List<RiderStandings> filterRiderStandingsByCategory(Category category) {
//        return riderStandings.stream()
//                .filter(standings -> standings.getCategory() == category)
//                .collect(Collectors.toList());
//    }
//
//    public List<TeamStandings> filterTeamStandingsByCategory(Category category) {
//        return teamStandings.stream()
//                .filter(standings -> standings.getCategory() == category)
//                .collect(Collectors.toList());
//    }
//
//    public List<ConstructorStandings> filterConstructorStandingsByCategory(Category category) {
//        return constructorStandings.stream()
//                .filter(standings -> standings.getCategory() == category)
//                .collect(Collectors.toList());
//    }

    public List<RiderStandings> sortRiderStandingsByPosition() {
        return riderStandings.stream()
                .sorted(Comparator.comparingInt(RiderStandings::getPosition))
                .collect(Collectors.toList());
    }

    public List<TeamStandings> sortTeamStandingsByPosition() {
        return teamStandings.stream()
                .sorted(Comparator.comparingInt(TeamStandings::getPosition))
                .collect(Collectors.toList());
    }

    public List<ConstructorStandings> sortConstructorStandingsByPosition() {
        return constructorStandings.stream()
                .sorted(Comparator.comparingInt(ConstructorStandings::getPosition))
                .collect(Collectors.toList());
    }



    // przykład do testowania
//    public List<RiderSession> getRiderSessionsByPosition(int position) {
//        List<RiderSession> riderSessions = new ArrayList<>();
//        for (RiderStandings standings : riderStandings) {
//            if (standings.getPosition() == position) {
//                riderSessions.add(new RiderSession(
//                        standings.getNumber(),
//                        standings.getName(),
//                        standings.getNationality(),
//                        standings.getTeam(),
//                        standings.getPosition(),
//                        0, // Pobierz czas sesji na podstawie pozycji
//                        0 // Pobierz liczbę okrążeń sesji na podstawie pozycji
//                ));
//            }
//        }
//        return riderSessions;
//    }
}
