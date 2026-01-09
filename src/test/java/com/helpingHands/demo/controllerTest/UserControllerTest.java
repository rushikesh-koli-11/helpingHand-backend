package com.helpingHands.demo.controllerTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpingHands.demo.DTO.UserDTO;
import com.helpingHands.demo.controller.UserController;
import com.helpingHands.demo.services.UserServices;
import com.helpingHands.demo.services.serviceImpl.RecaptchaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Base64;
import java.util.Collections;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserServices userServices;

    @Mock
    private RecaptchaService recaptchaService;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testLoginUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");
        userDTO.setRecaptchaToken("valid_token");

        when(recaptchaService.verifyRecaptcha("valid_token")).thenReturn(true);
        when(userServices.getUserByEmail("test@example.com")).thenReturn(userDTO);

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isUnauthorized()); 
    }

    @Test
    void testGetUserById() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setName("Test User");

        when(userServices.getUserById(1)).thenReturn(userDTO);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(userServices.getAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserByEmail() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");

        when(userServices.getUserByEmail("test@example.com")).thenReturn(userDTO);

        mockMvc.perform(get("/users/users/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testGetLatestUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setName("Latest User");

        when(userServices.getLatestUser()).thenReturn(userDTO);

        mockMvc.perform(get("/users/latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Latest User"));
    }

    @Test
    void testRegisterUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("New User");
        userDTO.setEmail("newuser@galaxe.com");
        userDTO.setPassword("password");
        userDTO.setContactNumber("1234567890");

        when(userServices.registerUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/users/register")
                .param("name", "New User")
                .param("email", "newuser@galaxe.com")
                .param("password", "password")
                .param("contactNumber", "1234567890"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New User"));
    }

    @Test
    void testUpdateUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setName("Updated User");

        when(userServices.updateUser(eq(1), any(UserDTO.class))).thenReturn(userDTO);

        mockMvc.perform(put("/users/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User"));
    }

    @Test
    void testDownloadFileAsBase64() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setProfilePicture("testImageData".getBytes());

        when(userServices.getFileById(1)).thenReturn(userDTO);

        mockMvc.perform(get("/users/download/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "testImageData".getBytes());

        mockMvc.perform(multipart("/users/upload")
                .file(file)
                .param("userId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testConvertBase64ToImage() throws Exception {
        String base64Image = Base64.getEncoder().encodeToString("testImageData".getBytes());

        mockMvc.perform(post("/users/convert-to-image")
                .contentType(MediaType.TEXT_PLAIN)
                .content(base64Image))
                .andExpect(status().isOk());
    }
}
