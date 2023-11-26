package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomRepository;
import com.epam.training.ticketservice.core.room.RoomService;
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
public class RoomCommand {
    private final RoomService roomService;
    private final UserService userService;

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create room", value = "Create Room")
    public String create(String name, Integer rows, Integer columns) {
        roomService.create(name, rows, columns);
        return "Room created!";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "update room", value = "Update Room")
    public String update(String name, Integer rows, Integer columns) {
        roomService.update(name, rows, columns);
        return "Room updated!";
    }

    @ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "delete room", value = "Delete Room")
    public String delete(String name) {
        roomService.delete(name);
        return "Room deleted!";
    }

    @ShellMethod(key = "list rooms", value = "List Rooms")
    public String list() {
        String output = "";
        List<Room> rooms = roomService.list();
        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        }
        for (Room r : rooms) {
            if (!output.equals("")) {
                output += "\n";
            }
            output += String.format("Room %s with %d seats, %d rows and %d columns",
                    r.getName(), r.getRows() * r.getColumns(), r.getRows(), r.getColumns());
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
