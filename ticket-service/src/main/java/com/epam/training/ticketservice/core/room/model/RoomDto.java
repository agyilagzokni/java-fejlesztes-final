package com.epam.training.ticketservice.core.room.model;

import lombok.Value;

@Value
public class RoomDto {
    private final String name;
    private final Integer rows;
    private final Integer columns;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private Integer rows;
        private Integer columns;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withRows(Integer rows) {
            this.rows = rows;
            return this;
        }

        public Builder withColumns(Integer columns) {
            this.columns = columns;
            return this;
        }

        public RoomDto build() {
            return new RoomDto(name, rows, columns);
        }
    }
}
