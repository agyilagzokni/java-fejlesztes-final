package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "Rooms")
@Data
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;
    private Integer rows;
    private Integer columns;

    public Room(String name, Integer rows, Integer columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    public Room(RoomDto dto) {
        this.name = dto.getName();
        this.rows = dto.getRows();
        this.columns = dto.getColumns();
    }
}
