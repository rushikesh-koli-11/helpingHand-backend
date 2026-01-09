package com.helpingHands.demo.controller;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.helpingHands.demo.DTO.UserDTO;
import com.helpingHands.demo.entities.User;
import com.helpingHands.demo.mapper.UserMapper;
import com.helpingHands.demo.repository.UserRepository;
import com.helpingHands.demo.services.UserServices;
import com.helpingHands.demo.services.serviceImpl.RecaptchaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	private final UserServices userServices;

	private final UserRepository userRepository;

	private final UserMapper userMapper;
	
    private final RecaptchaService recaptchaService; 
	

	// Logging in a user with reCAPTCHA verification
	@PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
		
        // Verifying reCAPTCHA before proceeding
        if (recaptchaService.verifyRecaptcha(userDTO.getRecaptchaToken())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("reCAPTCHA verification failed");
        }

        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
        }

        User user = optionalUser.get();

        // ✅ Check if password matches (Consider hashing it in production)
        if (!user.getPassword().equals(userDTO.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
        }

        UserDTO responseDTO = userMapper.toDTO(user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

	// Registering a new user
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("contactNumber") String contactNumber) {
		UserDTO userDto = new UserDTO();
		userDto.setName(name);
		userDto.setEmail(email);
		userDto.setPassword(password);
		userDto.setContactNumber(contactNumber);

		return ResponseEntity.ok(userServices.registerUser(userDto));
	}

	
	// Getting a user by their ID
	@GetMapping("/{userId}")
	public UserDTO getUserById(@PathVariable int userId) {
		return userServices.getUserById(userId);
	}

	// Getting all users
	@GetMapping
	public List<UserDTO> getAllUsers() {
		return userServices.getAllUsers();
	}

	// Getting a user by their email
	@GetMapping("/users/{email}")
	public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
		UserDTO user = userServices.getUserByEmail(email);
		return ResponseEntity.ok(user);
	}

	// Getting the latest registered user
	@GetMapping("/latest")
	public ResponseEntity<UserDTO> getLatestUser() {
		UserDTO latestUser = userServices.getLatestUser();
		return ResponseEntity.ok(latestUser);
	}

	// Updating a user's details
	@PutMapping("/update/{userId}")
    public UserDTO updateUser(@PathVariable int userId, @RequestBody UserDTO userDto) {
        return userServices.updateUser(userId, userDto);
    }

	// Downloading a user's profile picture as a Base64-encoded string
	@GetMapping("/download/{id}")
    public ResponseEntity<String> downloadFileAsBase64(@PathVariable int id) {
        UserDTO userDTO = userServices.getFileById(id);
        if (userDTO != null && userDTO.getProfilePicture() != null) {
            String base64Data = Base64.getEncoder().encodeToString(userDTO.getProfilePicture());
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(base64Data);
        }
        return ResponseEntity.notFound().build();
    }

	// Uploading a profile picture for a user
    @PutMapping("/upload")
    public UserDTO uploadFile(@RequestParam("file") MultipartFile file, @RequestParam int userId) {

    	User byId = userRepository.findById(userId).get();
    	UserDTO userDTO = new UserDTO();

        if (file != null && !file.isEmpty()) {
            byte[] fileBytes = convertFileToByteArray(file);
            byId.setProfilePicture(fileBytes);
            userRepository.save(byId);
            userMapper.toDTO(byId);
            return userDTO;
        }
        return null;
    }

    // Converting a Base64 string to an image
    @PostMapping("/convert-to-image")
    public ResponseEntity<byte[]> convertBase64ToImage(@RequestBody String base64Data) {
        try {
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);
            return ResponseEntity.ok()
                    .header("Content-Type", "image/jpeg")
                    .body(imageBytes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Helper method to convert a MultipartFile to a byte array
    private byte[] convertFileToByteArray(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Error converting file to byte array", e);
        }
    }
}
