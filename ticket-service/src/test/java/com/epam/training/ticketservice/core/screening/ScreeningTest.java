package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.room.RoomRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ScreeningTest {
    private static final Screening ENTITY = new Screening("Sátántangó", "Pedersoli",
            "2023-11-26 16:30");
    private static final ScreeningDto DTO = new ScreeningDto.Builder()
            .withName("Sátántangó")
            .withRoom("Pedersoli")
            .withTime("2023-11-26 16:30")
            .build();

    private final ScreeningRepository screeningRepository = mock(ScreeningRepository.class);

    private final RoomRepository roomRepository = mock(RoomRepository.class);

    private final ScreeningServiceImpl underTest = new ScreeningServiceImpl(screeningRepository, roomRepository);

    @Test
    void testFindByMovieAndRoomAndTimeShouldReturnScreeningWhenItExists(){
        //given
        when(screeningRepository.findByMovieAndRoomAndTime("Sátántangó", "Pedersoli",
                "2023-11-26 16:30")).thenReturn(Optional.of(ENTITY));
        Optional<ScreeningDto> excepted = Optional.of(DTO);

        //when
        Optional<ScreeningDto> actual = underTest.getScreening("Sátántangó", "Pedersoli",
                "2023-11-26 16:30");

        //then
        assertEquals(excepted, actual);
        verify(screeningRepository).findByMovieAndRoomAndTime("Sátántangó", "Pedersoli",
                "2023-11-26 16:30");
    }

    @Test
    void testFindByMovieAndRoomAndTimeShouldReturnEmptyOptionalWhenScreeningDoesNotExist(){
        //given
        when(screeningRepository.findByMovieAndRoomAndTime("Sátántangó", "Pedersoli",
                "2023-11-26 16:30")).thenReturn(Optional.empty());
        Optional<ScreeningDto> excepted = Optional.empty();

        //when
        Optional<ScreeningDto> actual = underTest.getScreening("Sátántangó", "Pedersoli",
                "2023-11-26 16:30");

        //then
        assertTrue(actual.isEmpty());
        assertEquals(excepted, actual);
        verify(screeningRepository).findByMovieAndRoomAndTime("Sátántangó", "Pedersoli",
                "2023-11-26 16:30");
    }

    @Test
    void testFindByMovieAndRoomAndTimeShouldReturnEmptyOptionalWhenInputIsNull(){
        //given
        when(screeningRepository.findByMovieAndRoomAndTime(null, null, null))
                .thenReturn(Optional.empty());
        Optional<ScreeningDto> excepted = Optional.empty();

        //when
        Optional<ScreeningDto> actual = underTest.getScreening(null, null, null);

        //then
        assertTrue(actual.isEmpty());
        assertEquals(excepted, actual);
        verify(screeningRepository).findByMovieAndRoomAndTime(null, null, null);
    }

    @Test
    void testScreeningShouldBeCreatedWhenValidScreening(){
        //given
        when(screeningRepository.save(ENTITY)).thenReturn(ENTITY);

        //when
        underTest.create(ENTITY.getMovie(), ENTITY.getRoom(), ENTITY.getTime());

        //then
        verify(screeningRepository).save(ENTITY);
    }

    @Test
    void testScreeningShouldBeDeletedWhenScreeningExists(){
        doNothing().when(screeningRepository).delete(ENTITY);

        underTest.delete(ENTITY.getMovie(), ENTITY.getRoom(), ENTITY.getTime());

        verify(screeningRepository).delete(ENTITY);
    }
}
