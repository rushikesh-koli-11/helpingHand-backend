package com.helpingHands.demo.mapperTest;

import static org.junit.jupiter.api.Assertions.*;

import com.helpingHands.demo.DTO.UserDTO;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void testToEntity() {
        byte[] profilePictureBytes = "sample image".getBytes(StandardCharsets.UTF_8);

        UserDTO userDTO = UserDTO.builder()
                .userId(1)
                .name("Om Parshetti")
                .email("om@galaxe.com")
                .password("password123")
                .contactNumber("1234567890")
                .recaptchaToken("test-token")
                .profilePicture(profilePictureBytes) 
                .build();

        User user = userMapper.toEntity(userDTO);

        assertNotNull(user);
        assertEquals(userDTO.getUserId(), user.getUserId());
        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getPassword(), user.getPassword());
        assertEquals(userDTO.getContactNumber(), user.getContactNumber());
        assertArrayEquals(userDTO.getProfilePicture(), user.getProfilePicture()); 
        assertEquals(userDTO.getRecaptchaToken(), user.getRecaptchaToken());
    }

    @Test
    void testToDTO() {
        byte[] profilePictureBytes = "sample image".getBytes(StandardCharsets.UTF_8);

        User user = new User();
        user.setUserId(1);
        user.setName("Rohit Mallade");
        user.setEmail("rohit@galaxe.com");
        user.setPassword("password123");
        user.setContactNumber("0987654321");
        user.setRecaptchaToken("test-token-2");
        user.setProfilePicture(profilePictureBytes); 

        UserDTO userDTO = userMapper.toDTO(user);

        assertNotNull(userDTO);
        assertEquals(user.getUserId(), userDTO.getUserId());
        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getContactNumber(), userDTO.getContactNumber());
        assertArrayEquals(user.getProfilePicture(), userDTO.getProfilePicture()); 
        assertEquals(user.getRecaptchaToken(), userDTO.getRecaptchaToken());
    }
}
