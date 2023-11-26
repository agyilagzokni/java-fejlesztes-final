package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoomTest {
    private static final Room ENTITY = new Room("Pedersoli", 20, 10);
    private static final RoomDto DTO = RoomDto.builder()
            .withName("Pedersoli")
            .withRows(20)
            .withColumns(10)
            .build();

    private final RoomRepository roomRepository = mock(RoomRepository.class);
    private final RoomServiceImpl underTest = new RoomServiceImpl(roomRepository);

    @Test
    void testFindByNameShouldReturnRoomWhenItExists(){
        //given
        when(roomRepository.findByName("Pedersoli")).thenReturn(Optional.of(ENTITY));
        Optional<RoomDto> excepted = Optional.of(DTO);

        //when
        Optional<RoomDto> actual = underTest.getRoom("Pedersoli");

        //then
        assertEquals(excepted, actual);
        verify(roomRepository).findByName("Pedersoli");
    }

    @Test
    void testFindByNameShouldReturnOptionalEmptyWhenItDoesNotExist(){
        //given
        when(roomRepository.findByName("Pedersoli")).thenReturn(Optional.empty());
        Optional<RoomDto> excepted = Optional.empty();

        //when
        Optional<RoomDto> actual = underTest.getRoom("Pedersoli");

        //then
        assertEquals(excepted, actual);
        verify(roomRepository).findByName("Pedersoli");
    }

    @Test
    void testFindByNameShouldReturnOptionalEmptyWhenInputIsNull(){
        //given
        when(roomRepository.findByName(null)).thenReturn(Optional.empty());
        Optional<RoomDto> excepted = Optional.empty();

        //when
        Optional<RoomDto> actual = underTest.getRoom(null);

        //then
        assertEquals(excepted, actual);
        verify(roomRepository).findByName(null);
    }
}
