package demosprings.service;

import demosprings.enity.Employee;
import demosprings.exception.ResourceNotFoundException;
import demosprings.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {


    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach

    void setUp() {
    userRepository = mock(UserRepository.class);
    userService = new UserServiceImpl(userRepository);
}

    @Test
    void testCreateUser() {
        Employee user = new Employee();
        user.setFirstname("R Govardhan Reddy");
        user.setLastname("rallapalli");

        when(userRepository.save(user)).thenReturn(user);

        Employee createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUser() {
        Employee existingUser = new Employee();
        existingUser.setId(1);
        existingUser.setFirstname("R Govardhan Reddy");

        Employee updatedUser = new Employee();
        updatedUser.setFirstname("R Govardhan");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        Employee result = userService.updateUser(1, updatedUser);

        assertEquals("R Govardhan", result.getFirstname());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void testDeleteUser() {
        Employee user = new Employee();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.deleteUser(1);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testGetUserById() {
        Employee user = new Employee();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Employee result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void testHardDelete() {
        doNothing().when(userRepository).deleteById(1);

        userService.hardDelete(1);

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void testSoftDeleteUser() {
        Employee user = new Employee();
        user.setId(1);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.softDeleteUser(1);

        assertTrue(user.isDeleted());
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }
}