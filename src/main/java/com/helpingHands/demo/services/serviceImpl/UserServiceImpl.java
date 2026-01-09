package com.helpingHands.demo.services.serviceImpl;

import com.helpingHands.demo.DTO.UserDTO;
import com.helpingHands.demo.constants.UserConstants;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.exception.CustomExceptions;
import com.helpingHands.demo.mapper.UserMapper;
import com.helpingHands.demo.repository.UserRepository;
import com.helpingHands.demo.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserServices} interface.
 * Provides business logic for user-related operations.
 * @author Om Parshetti
 **/
@Service
public class UserServiceImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    
    /**
     * Constructor for UserServiceImpl.
     * @param userRepository Repository for User entity.
     * @param userMapper Mapper to convert between User and UserDTO.
     */
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Authenticates a user by email and password.
     * @param email User's email.
     * @param password User's password.
     * @return true if authentication is successful, false otherwise.
     * @throws CustomExceptions if the user is not found.
     */
    @Override
    public boolean authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomExceptions(UserConstants.USER_NOT_FOUND));
        return user.getPassword().equals(password);
    }

    /**
     * Registers a new user.
     * @param userDTO Data transfer object containing user details.
     * @return The registered user as a DTO.
     * @throws CustomExceptions if the email is already used.
     */
    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new CustomExceptions(UserConstants.EMAIL_ALREADY_USED);
        }
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    /**
     * Retrieves a user by their ID.
     * @param userId The ID of the user.
     * @return The user as a DTO.
     * @throws CustomExceptions if the user is not found.
     */
    @Override
    public UserDTO getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomExceptions(UserConstants.USER_NOT_FOUND));
        return userMapper.toDTO(user);
    }

    /**
     * Retrieves a user by their email.
     * @param email The email of the user.
     * @return The user as a DTO.
     * @throws CustomExceptions if the user is not found.
     */
    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomExceptions(UserConstants.USER_NOT_FOUND_WITH_EMAIL + email));
        return userMapper.toDTO(user);
    }

    /**
     * Retrieves the most recently registered user.
     * @return The latest user as a DTO.
     * @throws CustomExceptions if no users are found.
     */
    @Override
    public UserDTO getLatestUser() {
        User latestUser = userRepository.findTopByOrderByUserIdDesc()
                .orElseThrow(() -> new CustomExceptions(UserConstants.NO_USERS_FOUND));
        return userMapper.toDTO(latestUser);
    }

    /**
     * Retrieves all users.
     * @return A list of user DTOs.
     * @throws CustomExceptions if no users are found.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new CustomExceptions(UserConstants.NO_USERS_FOUND);
        }
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    /**
     * Updates user information.
     * @param userId The ID of the user.
     * @param userDto DTO containing updated user details.
     * @return The updated user as a DTO.
     * @throws CustomExceptions if the user is not found.
     */
    @Override
    public UserDTO updateUser(String userId, UserDTO userDto) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomExceptions(UserConstants.USER_NOT_FOUND));

        existingUser.setName(userDto.getName());
        existingUser.setContactNumber(userDto.getContactNumber());

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    /**
     * Retrieves a user file by ID.
     * @param id The user ID.
     * @return The user DTO.
     * @throws CustomExceptions if the user is not found.
     */
    @Override
    public UserDTO getFileById(String id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new CustomExceptions(UserConstants.USER_NOT_FOUND));
    }

    /**
     * Saves a user's profile picture.
     * @param fileDTO DTO containing user profile picture.
     * @throws CustomExceptions if the user is not found.
     */
    @Override
    public void saveFile(UserDTO fileDTO) {
        User user = userRepository.findById(fileDTO.getUserId())
                .orElseThrow(() -> new CustomExceptions(UserConstants.USER_NOT_FOUND));
        user.setProfilePicture(fileDTO.getProfilePicture());
        userRepository.save(user);
    }
}
