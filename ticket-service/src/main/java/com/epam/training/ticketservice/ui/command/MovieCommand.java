package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.user.User;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class MovieCommand {
    private final MovieService movieService;
    private final UserService userService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create movie", value = "Create Movie")
    public String create(String name, String genre, Integer length) {
        movieService.create(name, genre, length);
        return "Movie added!";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update movie", value = "Update Movie")
    public String update(String name, String genre, Integer length) {
        movieService.update(name, genre, length);
        return "Movie updated!";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete movie", value = "Delete Movie")
    public String delete(String name) {
        movieService.delete(name);
        return "Movie deleted!";
    }

    @ShellMethod(key = "list movies", value = "List Movies")
    public String list() {
        String output = "";
        List<Movie> movies = movieService.list();
        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        }
        for (Movie m : movies) {
            if (!output.equals("")) {
                output += "\n";
            }
            output += String.format("%s (%s, %d minutes)", m.getName(), m.getGenre(), m.getLength());
        }
        return output;
    }

    public Availability isAvailable() {
        Optional<UserDto> user = userService.describe();
        if (user.isEmpty()) {
            return Availability.unavailable("You are not logged in!");
        } else if (user.get().role() != User.Role.ADMIN) {
            return Availability.unavailable("You are not an admin!");
        }
        return Availability.available();
    }
}
