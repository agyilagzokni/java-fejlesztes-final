package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class UserCommand {
    private final UserService userService;

    @ShellMethod(key = "sign in privileged", value = "Admin Login")
    public String login(String username, String password) {
        return userService.login(username, password)
                .map(userDto -> userDto + " has logged in!")
                .orElse("Login failed due to incorrect credentials");
    }

    @ShellMethod(key = "sign out", value = "Sign Out")
    public String logout() {
        return userService.logout()
                .map(userDto -> userDto.username() + " has logged out!")
                .orElse("You need to log in first");
    }

    @ShellMethod(key = "describe account", value = "Describe Account")
    public String describeAccount() {
        return userService.describe()
                .map(userDto -> "Signed in with privileged account '" + userDto.username() + "'")
                .orElse("You are not signed in");
    }
}
