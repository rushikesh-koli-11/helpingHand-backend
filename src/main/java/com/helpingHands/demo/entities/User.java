package com.helpingHands.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String userId;
    
    private String name;
    
    @Indexed(unique = true)
    private String email;
    private String password;
    private String contactNumber;
    private String recaptchaToken;
    
    private String profilePicture; // Cloudinary URL
}

