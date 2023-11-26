package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    void create(String name, String genre, Integer length);

    void update(String name, String genre, Integer length);

    void delete(String name);

    Optional<MovieDto> getMovie(String name);

    List<Movie> list();
}
