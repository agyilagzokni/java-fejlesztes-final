package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MovieTest {

    private static final Movie MOVIE = new Movie("Sátántangó", "drama", 450);

    private static final MovieDto DTO = MovieDto.builder()
            .withName("Sátántangó")
            .withGenre("drama")
            .withLength(450)
            .build();

    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieServiceImpl underTest = new MovieServiceImpl(movieRepository);

    @Test
    void testFindMovieByNameShouldReturnMovieWhenItExists(){
        //given
        when(movieRepository.findMovieByName("Sátántangó")).thenReturn(Optional.of(MOVIE));
        Optional<MovieDto> excepted = Optional.of(DTO);

        //when
        Optional<MovieDto> actual = underTest.getMovie("Sátántangó");

        //then
        assertEquals(excepted, actual);
        verify(movieRepository).findMovieByName("Sátántangó");
    }

    @Test
    void testFindMovieByNameShouldReturnOptionalEmptyWhenItDoesNotExists(){
        //given
        when(movieRepository.findMovieByName("Sátántangó")).thenReturn(Optional.empty());
        Optional<MovieDto> excepted = Optional.empty();

        //when
        Optional<MovieDto> actual = underTest.getMovie("Sátántangó");

        //then
        assertEquals(excepted, actual);
        verify(movieRepository).findMovieByName("Sátántangó");
    }

    @Test
    void testFindMovieByNameShouldReturnOptionalEmptyWhenInputIsNull(){
        //given
        when(movieRepository.findMovieByName(null)).thenReturn(Optional.empty());
        Optional<MovieDto> excepted = Optional.empty();

        //when
        Optional<MovieDto> actual = underTest.getMovie(null);

        //then
        assertEquals(excepted, actual);
        verify(movieRepository).findMovieByName(null);
    }

    @Test
    void testMovieCreation(){
        MovieServiceImpl movieService1 = mock(MovieServiceImpl.class);

        movieService1.create(MOVIE.getName(), MOVIE.getGenre(), MOVIE.getLength());

        verify(movieService1, times(1)).create(MOVIE.getName(), MOVIE.getGenre(),
                MOVIE.getLength());
    }

}
