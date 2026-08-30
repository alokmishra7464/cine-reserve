package com.cinereserve.cine_reserve.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String title;

    private List<Show> shows = new ArrayList<>();

    @OneToMany(mappedBy = "movie")
    public void addShows(Show show) {
        shows.add(show);
        show.setMovie(this);
    }

    public Movie() {

    }
    public Movie(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
