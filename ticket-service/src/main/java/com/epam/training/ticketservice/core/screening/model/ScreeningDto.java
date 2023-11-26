package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.room.Room;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
public class ScreeningDto {
    private final String name;
    private final String room;
    private final String time;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String roomName;
        private String time;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withRoom(String roomName) {
            this.roomName = roomName;
            return this;
        }

        public Builder withTime(String time) {
            this.time = time;
            return this;
        }

        public ScreeningDto build() {
            return new ScreeningDto(name, roomName, time);
        }
    }
}
