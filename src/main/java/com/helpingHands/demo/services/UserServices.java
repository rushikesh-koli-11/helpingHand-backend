package com.helpingHands.demo.services;

import java.util.List;

import com.helpingHands.demo.DTO.UserDTO;

public interface UserServices {
	
    UserDTO registerUser(UserDTO userDto);
    boolean authenticateUser(String email, String password);
    UserDTO getUserById(String userId);
    UserDTO updateUser(String userId, UserDTO userDto);
    List<UserDTO> getAllUsers();
    UserDTO getUserByEmail(String email);
    UserDTO getLatestUser();
    
    UserDTO getFileById(String id);
    void saveFile(UserDTO fileDTO);
}
