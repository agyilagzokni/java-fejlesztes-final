package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;


@Entity
@Table(name = "Movies")
@Data
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;
    private String genre;
    private Integer length;

    public Movie(String name, String genre, Integer length) {
        this.name = name;
        this.genre = genre;
        this.length = length;
    }

    public Movie(MovieDto dto) {
        this.name = dto.getName();
        this.genre = dto.getGenre();
        this.length = dto.getLength();
    }

}
