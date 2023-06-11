package com.example.TestBackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "race_result_view")
public class RaceResultView {
    @Id
    private Long id;
    private String riderName;
    private String teamName;
    private int raceNumber;
    private String category;

}
