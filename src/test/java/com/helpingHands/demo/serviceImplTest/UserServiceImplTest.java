package com.helpingHands.demo.serviceImplTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.helpingHands.demo.DTO.UserDTO;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.UserMapper;
import com.helpingHands.demo.repository.UserRepository;
import com.helpingHands.demo.services.serviceImpl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1);
        user.setEmail("test@example.com");
        user.setPassword("password");

        userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setEmail("test@example.com");
    }

    @Test
    void testAuthenticateUser_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        boolean isAuthenticated = userService.authenticateUser("test@example.com", "password");

        assertTrue(isAuthenticated);
    }

    @Test
    void testAuthenticateUser_Failure() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        boolean isAuthenticated = userService.authenticateUser("test@example.com", "wrongPassword");

        assertFalse(isAuthenticated);
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        UserDTO result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals(userDTO.getEmail(), result.getEmail());
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CustomExceptions.class, () -> userService.getUserById(1));
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        List<UserDTO> result = userService.getAllUsers();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}
