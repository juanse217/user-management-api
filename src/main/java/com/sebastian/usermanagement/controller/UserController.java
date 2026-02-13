package com.sebastian.usermanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sebastian.usermanagement.dto.CreateUserDTO;
import com.sebastian.usermanagement.dto.UpdatePasswordDTO;
import com.sebastian.usermanagement.dto.UpdateUserDTO;
import com.sebastian.usermanagement.dto.UserDTO;
import com.sebastian.usermanagement.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAllUsers(@RequestHeader("X-User-Id") Long requestingUser) {
        return ResponseEntity.ok(service.findAllUsers(requestingUser));
    }
    
    @GetMapping("/{targetId}")
    public ResponseEntity<UserDTO> findUserById(@RequestHeader("X-User-Id") Long requestingUser, @PathVariable Long targetId) {
        return ResponseEntity.ok(service.findUser(requestingUser, targetId));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO createDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createUser(createDto));
    }

    @DeleteMapping("/{targetId}")
    public ResponseEntity<Void> deleteUser(@RequestHeader("X-User-Id") Long requestingUser, @PathVariable Long targetId){
        service.deleteUser(requestingUser, targetId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{targetId}/password")
    public ResponseEntity<Void> updatePassword(@RequestHeader("X-User-Id") Long requestingUser, @PathVariable Long targetId, @RequestBody UpdatePasswordDTO pwDTO){
        service.updatePassword(requestingUser, targetId, pwDTO);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{targetId}")//No need for specific path since we're updating the main resource, not a specific like only the password. Password updates have different validation, are special operations (require current password). 
    public ResponseEntity<UserDTO> updateUserInfo(@RequestHeader("X-User-Id") Long requestingUser, @PathVariable Long targetId, @RequestBody UpdateUserDTO updateDto){

        return ResponseEntity.ok(service.updateUserInfo(requestingUser, targetId, updateDto));
    }
}
