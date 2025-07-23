package com.should_i_bunk.should_i_bunk.user.mapper;

import com.should_i_bunk.should_i_bunk.auth.request.RegistrationRequest;
import com.should_i_bunk.should_i_bunk.user.User;
import com.should_i_bunk.should_i_bunk.user.request.ProfileUpdateRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    // This class can be used to map User entities to DTOs and vice versa.
    // Currently, it is empty, but you can add methods here as needed.

    // Example method to convert User entity to UserDTO
    // public UserDTO toDto(User user) {
    //     return new UserDTO(user.getId(), user.getEmail(), user.getName());
    // }

    // Example method to convert UserDTO to User entity
    // public User toEntity(UserDTO userDTO) {
    //     return new User(userDTO.getId(), userDTO.getEmail(), userDTO.getName());
    // }

    // Add more mapping methods as needed for your application
    public void mergerUserInfo(final User saveduser, final ProfileUpdateRequest request) {
        // Implement the logic to merge user information from request into saveduser
        // For example:
        // saveduser.setName(request.getName());
        // saveduser.setEmail(request.getEmail());
        // etc.
        if(StringUtils.isNotBlank(request.getFirstName())
                                              &&!saveduser.getFirstName().equals(request.getFirstName())) {
            saveduser.setFirstName(request.getFirstName());
        }
        if(StringUtils.isNotBlank(request.getLastName())
                &&!saveduser.getLastName().equals(request.getLastName())) {
            saveduser.setLastName(request.getLastName());
        }



    }

    public User toUser(final RegistrationRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .enabled(true)
                .locked(false)
                .credentialsExpired(false)
                .build();
    }


}
