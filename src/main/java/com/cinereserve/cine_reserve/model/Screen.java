package com.cinereserve.cine_reserve.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "screens")
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "screen")
    private List<Show> shows = new ArrayList<>();


    public void addShow(Show show) {
        shows.add(show);
        show.setScreen(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @OneToMany(mappedBy = "screen")
    private List<Seat> seats = new ArrayList<>();

    public void setSeats(Seat seat) {
        seats.add(seat);
        seat.setScreen(this);
    }

}
