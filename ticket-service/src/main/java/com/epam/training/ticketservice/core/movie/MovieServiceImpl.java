package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;


    @Override
    public void create(String name, String genre, Integer length) {
        Movie movie = new Movie(name, genre, length);
        movieRepository.save(movie);
    }

    @Override
    public void update(String name, String genre, Integer length) {
        Optional<Movie> movie = movieRepository.findMovieByName(name);
        movieRepository.delete(movie.get());
        movieRepository.save(new Movie(name, genre, length));
    }

    @Override
    public void delete(String name) {
        Optional<Movie> movie = movieRepository.findMovieByName(name);
        movieRepository.delete(movie.get());
    }

    public Optional<MovieDto> createDtoFromEntity(Optional<Movie> movie) {
        try {
            return Optional.ofNullable(MovieDto.builder()
                    .withName(movie.get().getName())
                    .withGenre(movie.get().getGenre())
                    .withLength(movie.get().getLength())
                    .build());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<MovieDto> getMovie(String movieName) {
        return createDtoFromEntity(movieRepository.findMovieByName(movieName));
    }


    @Override
    public List<Movie> list() {
        return movieRepository.findAll();
    }
}
