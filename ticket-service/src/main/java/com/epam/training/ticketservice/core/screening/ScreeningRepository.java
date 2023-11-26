package com.epam.training.ticketservice.core.screening;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Integer> {
    Optional<Screening> findByMovieAndRoomAndTime(String movie, String room, String time);
}
