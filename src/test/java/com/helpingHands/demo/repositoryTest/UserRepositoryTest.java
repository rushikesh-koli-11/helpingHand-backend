//package com.helpingHands.demo.repositoryTest;
//
//import com.helpingHands.demo.entities.User;
//import com.helpingHands.demo.repository.UserRepository;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Transactional
//class UserRepositoryTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    void testFindByEmail() {
//        // Arrange
//        User user = new User();
//        user.setEmail("test@example.com");
//        user.setName("Test User");
//        userRepository.save(user);
//
//        // Act
//        Optional<User> foundUser = userRepository.findByEmail("test@example.com");
//
//        // Assert
//        assertTrue(foundUser.isPresent());
//        assertEquals("test@example.com", foundUser.get().getEmail());
//    }
//
//    @Test
//    void testFindByEmail_NotFound() {
//        // Act
//        Optional<User> foundUser = userRepository.findByEmail("notfound@example.com");
//
//        // Assert
//        assertFalse(foundUser.isPresent());
//    }
//
//    @Test
//    void testFindTopByOrderByUserIdDesc() {
//        // Arrange
//        User user1 = new User();
//        user1.setEmail("user1@example.com");
//        user1.setName("User 1");
//        userRepository.save(user1);
//
//        User user2 = new User();
//        user2.setEmail("user2@example.com");
//        user2.setName("User 2");
//        userRepository.save(user2);
//
//        // Act
//        Optional<User> latestUser = userRepository.findTopByOrderByUserIdDesc();
//
//        // Assert
//        assertTrue(latestUser.isPresent());
//        assertEquals("user2@example.com", latestUser.get().getEmail());
//    }
//}
