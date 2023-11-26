package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.MovieRepository;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.MovieServiceImpl;
import com.epam.training.ticketservice.core.user.User;
import com.epam.training.ticketservice.core.user.UserRepository;
import com.epam.training.ticketservice.core.user.UserServiceImpl;
import com.epam.training.ticketservice.core.user.model.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Availability;
import org.springframework.shell.Shell;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.shell.Shell;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MovieCommandTest {

    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);

    private final MovieServiceImpl movieService = mock(MovieServiceImpl.class);

    private final UserServiceImpl userService = mock(UserServiceImpl.class);

    private final MovieCommand movieCommand = new MovieCommand(movieService, userService);

    @Test
    void testMovieListShouldReturnNoMoviesStringWhenTheListIsEmpty(){
        when(movieService.list()).thenReturn(emptyList());
        String excepted = "There are no movies at the moment";

        String actual = movieCommand.list();

        assertEquals(excepted, actual);
        verify(movieService).list();
    }

    @Test
    void testMovieListShouldReturnMovieListWhenTheListIsNotEmpty(){
        when(movieService.list()).thenReturn(List.of(new Movie("Sátántangó", "drama", 450)));
        String excepted = "Sátántangó (drama, 450 minutes)";

        String actual = movieCommand.list();

        assertEquals(excepted, actual);
        verify(movieService).list();
    }

    @Test
    void testCommandShouldBeAvailableWhenAdminIsLoggedIn(){
        when(userService.describe()).thenReturn(Optional.of(new UserDto("admin", User.Role.ADMIN)));
        Availability excepted = Availability.available();

        Availability actual = movieCommand.isAvailable();

        assertEquals(excepted.isAvailable(), actual.isAvailable());
        verify(userService).describe();
    }

    @Test
    void testCommandShouldBeUnavailableWhenNobodyIsLoggedIn(){
        when(userService.describe()).thenReturn(Optional.empty());
        Availability excepted = Availability.unavailable("You are not logged in!");

        Availability actual = movieCommand.isAvailable();

        assertEquals(excepted.isAvailable(), actual.isAvailable());
        verify(userService).describe();
    }

    @Test
    void testCommandShouldBeUnavailableWhenRegularUserIsLoggedIn(){
        when(userService.describe()).thenReturn(Optional.of(new UserDto("user", User.Role.USER)));
        Availability excepted = Availability.unavailable("You are not an admin!");

        Availability actual = movieCommand.isAvailable();

        assertEquals(excepted.isAvailable(), actual.isAvailable());
        verify(userService).describe();
    }
}
