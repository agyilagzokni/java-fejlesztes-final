package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.Movie;
import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.room.Room;
import com.epam.training.ticketservice.core.room.RoomRepository;
import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.swing.text.DateFormatter;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "Screenings")
@Data
@NoArgsConstructor
public class Screening {
    @Id
    @GeneratedValue
    private Integer id;
    private String movie;
    private String room;
    private String time;

    public Screening(String movie, String room, String dateAndTime) {
        this.movie = movie;
        this.room = room;
        this.time = dateAndTime;
    }

    public Screening(ScreeningDto dto) {
        this.movie = dto.getName();
        this.room = dto.getRoom();
        this.time = dto.getTime();
    }
}
