//package com.helpingHands.demo.DTO;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class UserDTO {
//    
//	private int userId;
//    private String name;
//    private String email;
//    private String password;
//    private String contactNumber;
//    private String profilePicture;
//
//}

package com.helpingHands.demo.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
	private String userId;
	private List<String> fundraiserIds;
	private String name;
	private String email;
	private String password;
	private String contactNumber;
	private String recaptchaToken;
	private String profilePicture; // Cloudinary URL
}
