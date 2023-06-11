package com.example.TestBackend.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private int number;
    private String name;
    private String team;
    // konstruktor bezarg
    public Rider() {}

    public Rider(int number, String name, String team) {
        this.number = number;
        this.name = name;
        this.team = team;
    }
    //get set


    @Override
    public String toString() {
        return "Rider{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", team='" + team + '\'' +

                '}';
    }
}








