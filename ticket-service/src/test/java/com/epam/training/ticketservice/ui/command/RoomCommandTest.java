package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.MovieRepository;
import com.epam.training.ticketservice.core.movie.MovieServiceImpl;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomRepository;
import com.epam.training.ticketservice.core.room.RoomServiceImpl;
import com.epam.training.ticketservice.core.user.UserRepository;
import com.epam.training.ticketservice.core.user.UserServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoomCommandTest {
    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);

    private final RoomServiceImpl roomService = mock(RoomServiceImpl.class);

    private final UserServiceImpl userService = mock(UserServiceImpl.class);

    private final RoomCommand roomCommand = new RoomCommand(roomService, userService);

    @Test
    void testRoomListShouldReturnNoRoomsStringWhenTheListIsEmpty(){
        when(roomService.list()).thenReturn(emptyList());
        String excepted = "There are no rooms at the moment";

        String actual = roomCommand.list();

        assertEquals(excepted, actual);
        verify(roomService).list();
    }

    @Test
    void testMovieListShouldReturnMovieListWhenTheListIsNotEmpty(){
        when(roomService.list()).thenReturn(List.of(new Room("Pedersoli", 20, 10)));
        String excepted = "Room Pedersoli with 200 seats, 20 rows and 10 columns";

        String actual = roomCommand.list();

        assertEquals(excepted, actual);
        verify(roomService).list();
    }
}
