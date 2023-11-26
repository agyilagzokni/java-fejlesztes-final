package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.room.model.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public void create(String name, Integer rows, Integer columns) {
        Room room = new Room(name, rows, columns);
        roomRepository.save(room);
    }

    @Override
    public void update(String name, Integer rows, Integer columns) {
        Optional<Room> room = roomRepository.findByName(name);
        roomRepository.delete(room.get());
        roomRepository.save(new Room(name, rows, columns));
    }

    @Override
    public void delete(String name) {
        Optional<Room> room = roomRepository.findByName(name);
        roomRepository.delete(room.get());
    }

    public Optional<RoomDto> createDtoFromEntity(Optional<Room> room) {
        try {
            return Optional.ofNullable(RoomDto.builder()
                    .withName(room.get().getName())
                    .withRows(room.get().getRows())
                    .withColumns(room.get().getColumns())
                    .build());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RoomDto> getRoom(String name) {
        return createDtoFromEntity(roomRepository.findByName(name));
    }

    @Override
    public List<Room> list() {
        return roomRepository.findAll();
    }
}
