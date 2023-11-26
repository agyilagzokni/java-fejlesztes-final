package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.MovieRepository;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final RoomRepository roomRepository;

    @Override
    public void create(String movieName, String roomName, String dateAndTime) {
        Screening screening = new Screening(movieName, roomName, dateAndTime);
        screeningRepository.save(screening);
    }

    @Override
    public void delete(String movieName, String roomName, String dateAndTime) {
        Screening screening = new Screening(movieName, roomName, dateAndTime);
        screeningRepository.delete(screening);
    }

    public Optional<ScreeningDto> createDtoFromEntity(Optional<Screening> screening) {
        try {
            return Optional.ofNullable(ScreeningDto.builder()
                    .withName(screening.get().getMovie())
                    .withRoom(screening.get().getRoom())
                    .withTime(screening.get().getTime())
                    .build());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ScreeningDto> getScreening(String movieName, String roomName, String dateAndTime) {
        return createDtoFromEntity(screeningRepository.findByMovieAndRoomAndTime(movieName, roomName, dateAndTime));
    }

    @Override
    public List<Screening> list() {
        return screeningRepository.findAll();
    }
}
