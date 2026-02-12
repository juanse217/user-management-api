package com.sebastian.usermanagement.service;

import org.springframework.stereotype.Component;

import com.sebastian.usermanagement.dto.CreateUserDTO;
import com.sebastian.usermanagement.dto.UpdatePasswordDTO;
import com.sebastian.usermanagement.dto.UpdateUserDTO;

@Component
public class UserValidator {

    private static final int MIN_CHAR_PASSWORD = 8;

    public void validateUpdate(UpdateUserDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Update data cannot be null");
        }
        
        // At least one field must be present
        if (dto.getName() == null && dto.getRole() == null) {
            throw new IllegalArgumentException("At least one field must be provided for update");
        }
        
        // Validate name if provided
        if (dto.getName() != null && dto.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

    public void validatePasswordUpdate(UpdatePasswordDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("The update data cannot be null");
        }
        if (dto.getCurrentPassword() == null) {
            throw new IllegalArgumentException("Current password is required");
        }
        if (dto.getNewPassword() == null || dto.getNewPassword().length() < MIN_CHAR_PASSWORD) {
            throw new IllegalArgumentException("New password must be at least 8 characters long");
        }
    }

    public void validateId(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("The id cannot be null");
        }
    }
}
