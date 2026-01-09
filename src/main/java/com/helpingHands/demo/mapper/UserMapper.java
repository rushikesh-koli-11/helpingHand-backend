//package com.helpingHands.demo.mapper;
//
//import org.springframework.stereotype.Component;
//
//import com.helpingHands.demo.DTO.UserDTO;
//import com.helpingHands.demo.entities.User;
//
//@Component
//public class UserMapper {
//	
//	public User toEntity(UserDTO dto) {
//		User user = new User();
//		user.setUserId(dto.getUserId());
//		user.setName(dto.getName());
//		user.setEmail(dto.getEmail());
//		user.setPassword(dto.getPassword());
//		user.setContactNumber(dto.getContactNumber());
//		return user;
//	}
//	
//	public UserDTO toDTO(User user) {
//		UserDTO dto = new UserDTO();
//		dto.setUserId(user.getUserId());
//		dto.setName(user.getName());
//		dto.setEmail(user.getEmail());
//		dto.setPassword(user.getPassword());
//		dto.setContactNumber(user.getContactNumber());
//		return dto;
//	}
//}

package com.helpingHands.demo.mapper;

import com.helpingHands.demo.DTO.UserDTO;
import com.helpingHands.demo.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setContactNumber(userDTO.getContactNumber());
        user.setProfilePicture(userDTO.getProfilePicture());
        user.setRecaptchaToken(userDTO.getRecaptchaToken());
        return user;
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getUserId(),
        				   null, // fundraiserIds - not stored in User entity
        				   user.getName(), 
        				   user.getEmail(), 
        				   user.getPassword(), 
        				   user.getContactNumber(),
        				   user.getRecaptchaToken(),
        				   user.getProfilePicture()
        				  );
    }
    
    
}
