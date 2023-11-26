package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.MovieRepository;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomRepository;
import com.epam.training.ticketservice.core.room.RoomServiceImpl;
import com.epam.training.ticketservice.core.screening.Screening;
import com.epam.training.ticketservice.core.screening.ScreeningServiceImpl;
import com.epam.training.ticketservice.core.user.UserRepository;
import com.epam.training.ticketservice.core.user.UserServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ScreeningCommandTest {

    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);

    private final ScreeningServiceImpl screeningService = mock(ScreeningServiceImpl.class);

    private final UserServiceImpl userService = mock(UserServiceImpl.class);

    private final ScreeningCommand screeningCommand = new ScreeningCommand(movieRepository, roomRepository, screeningService, userService);

    @Test
    void testScreeningListShouldReturnNoScreeningsStringWhenTheListIsEmpty(){
        when(screeningService.list()).thenReturn(emptyList());
        String excepted = "There are no screenings";

        String actual = screeningCommand.list();

        assertEquals(excepted, actual);
        verify(screeningService).list();
    }

    @Test
    void testMovieListShouldReturnMovieListWhenTheListIsNotEmpty(){
        when(movieRepository.findMovieByName("Sátántangó")).thenReturn(Optional.of(new Movie("Sátántangó", "drama", 450)));
        when(roomRepository.findByName("Pedersoli")).thenReturn(Optional.of(new Room("Pedersoli", 20, 10)));
        when(screeningService.list()).thenReturn(List.of(new Screening("Sátántangó", "Pedersoli",
                "2023-11-26 22:10")));
        String excepted = "Sátántangó (drama, 450 minutes), screened in room Pedersoli, at 2023-11-26 22:10";

        String actual = screeningCommand.list();

        assertEquals(excepted, actual);
        verify(screeningService).list();
    }
}
