package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.MovieRepository;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomRepository;
import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.user.User;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class ScreeningCommand {
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    private final ScreeningService screeningService;
    private final UserService userService;

    private final Integer breakPeriod = 10;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening", value = "Create Screening")
    public String create(String movieName, String roomName, String dateAndTime) {
        Room room = roomRepository.findByName(roomName).get();
        LocalDateTime localDateTime = LocalDateTime.parse(dateAndTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        for (Screening s : screeningService.list()) {
            LocalDateTime movieStart = LocalDateTime.parse(s.getTime(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime movieEnd = LocalDateTime.parse(s.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    .plusMinutes(movieRepository.findMovieByName(s.getMovie()).get().getLength());
            if (s.getRoom().equals(room.getName())) {
                //another movie is being screened
                if (localDateTime.isAfter(movieStart) && localDateTime.isBefore(movieEnd)) {
                    return "There is an overlapping screening";
                    //overlapping with the break period
                } else if (localDateTime.isAfter(movieEnd)
                        && localDateTime.isBefore(movieEnd.plusMinutes(breakPeriod))) {
                    return "This would start in the break period after another screening in this room";
                }
            }
        }
        try {
            screeningService.create(movieName, roomName, dateAndTime);
            return "Screening created";
        } catch (Exception e) {
            return "Screening creation failed";
        }
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete screening", value = "Delete Screening")
    public String delete(String movieName, String roomName, String dateAndTime) {
        try {
            screeningService.delete(movieName, roomName, dateAndTime);
            return "Screening deleted!";
        } catch (Exception e) {
            return "Screening deletion failed!";
        }
    }

    @ShellMethod(key = "list screenings", value = "List Screenings")
    public String list() {
        String output = "";
        List<Screening> screenings = screeningService.list();
        if (screenings.isEmpty()) {
            return "There are no screenings";
        }
        for (Screening s : screenings) {
            if (!output.equals("")) {
                output += "\n";
            }
            Movie movie = movieRepository.findMovieByName(s.getMovie()).get();
            Room room = roomRepository.findByName(s.getRoom()).get();
            output += String.format("%s (%s, %d minutes), screened in room %s, at %s",
                    movie.getName(), movie.getGenre(), movie.getLength(), room.getName(), s.getTime());
        }
        return output;
    }

    private Availability isAvailable() {
        Optional<UserDto> user = userService.describe();
        if (user.isEmpty()) {
            return Availability.unavailable("You are not logged in!");
        } else if (user.get().role() != User.Role.ADMIN) {
            return Availability.unavailable("You are not an admin!");
        }
        return Availability.available();
    }
}
