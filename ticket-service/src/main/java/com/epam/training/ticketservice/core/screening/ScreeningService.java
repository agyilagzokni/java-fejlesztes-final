package com.epam.training.ticketservice.core.screening;


import com.epam.training.ticketservice.core.screening.model.ScreeningDto;

import java.util.List;
import java.util.Optional;

public interface ScreeningService {
    void create(String movieName, String roomName, String dateAndTime);

    void delete(String movieName, String roomName, String dateAndTime);

    Optional<ScreeningDto> getScreening(String movieName, String roomName, String dateAndTime);

    List<Screening> list();
}
