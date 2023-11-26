package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    void create(String name, Integer rows, Integer columns);

    void update(String name, Integer rows, Integer columns);

    void delete(String name);

    Optional<RoomDto> getRoom(String name);

    List<Room> list();
}
