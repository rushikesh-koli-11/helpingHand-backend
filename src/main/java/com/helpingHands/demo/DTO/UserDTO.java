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
	private int userId;
	private List<Integer> fundraiserIds;
	private String name;
	private String email;
	private String password;
	private String contactNumber;
	private String recaptchaToken;

	private byte[] profilePicture;

	public UserDTO(int userId, String name, String email, String password, String contactNumber, String recaptchaToken,
			byte[] profilePicture) {
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.contactNumber = contactNumber;
		this.recaptchaToken = recaptchaToken;
		this.profilePicture = profilePicture;
	}

}
