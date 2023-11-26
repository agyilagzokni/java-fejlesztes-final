package com.epam.training.ticketservice.core.user.model;

import com.epam.training.ticketservice.core.user.User;

public record UserDto(String username, User.Role role) {
    @Override
    public String toString() {
        return String.format("User %s, Role: %s", username, role.toString());
    }
}
