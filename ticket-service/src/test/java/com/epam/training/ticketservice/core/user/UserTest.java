package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.model.UserDto;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserServiceImpl underTest = new UserServiceImpl(userRepository);

    @Test
    void testLoginShouldReturnDTOWhenCorrectCredentialsAreGiven(){
        User user = new User("admin", "admin", User.Role.ADMIN);
        Optional<User> excepted = Optional.of(user);

        when(userRepository.findByUsernameAndPassword("admin", "admin")).thenReturn(Optional.of(user));

        Optional<UserDto> actual = underTest.login("admin", "admin");

        assertEquals(excepted.get().getUsername(), actual.get().username());
        assertEquals(excepted.get().getRole(), actual.get().role());
        verify(userRepository).findByUsernameAndPassword("admin", "admin");
    }

    @Test
    void testLoginShouldReturnOptionalEmptyWhenUsernameOrPasswordNotCorrect(){
        Optional<UserDto> excepted = Optional.empty();
        when(userRepository.findByUsernameAndPassword("asd", "asd")).thenReturn(Optional.empty());

        Optional<UserDto> actual = underTest.login("asd", "asd");

        assertEquals(excepted, actual);
        verify(userRepository).findByUsernameAndPassword("asd", "asd");
    }

    @Test
    void testLogoutShouldReturnThePreviouslyLoggedInUserWhenThereIsALoggedInUser(){
        User user = new User("admin", "admin", User.Role.ADMIN);
        when(userRepository.findByUsernameAndPassword("admin", "admin")).thenReturn(Optional.of(user));
        Optional<UserDto> expected = underTest.login("admin", "admin");

        Optional<UserDto> actual = underTest.logout();

        assertEquals(expected, actual);
    }

    @Test
    void testDescribeShouldReturnTheLoggedInUserWhenThereIsALoggedInUser() {
        User user = new User("asd", "asd", User.Role.USER);
        when(userRepository.findByUsernameAndPassword("asd", "asd")).thenReturn(Optional.of(user));
        Optional<UserDto> expected = underTest.login("asd", "asd");

        Optional<UserDto> actual = underTest.describe();

        assertEquals(expected, actual);
    }

    @Test
    void testDescribeShouldReturnOptionalEmptyWhenThereIsNoOneLoggedIn() {
        Optional<UserDto> expected = Optional.empty();

        Optional<UserDto> actual = underTest.describe();

        assertEquals(expected, actual);
    }
}
