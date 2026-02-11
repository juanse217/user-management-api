package com.sebastian.usermanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sebastian.usermanagement.dto.CreateUserDTO;
import com.sebastian.usermanagement.dto.UpdatePasswordDTO;
import com.sebastian.usermanagement.dto.UpdateUserDTO;
import com.sebastian.usermanagement.dto.UserDTO;
import com.sebastian.usermanagement.exception.UnauthorizedException;
import com.sebastian.usermanagement.exception.UserNotfoundException;
import com.sebastian.usermanagement.model.User;
import com.sebastian.usermanagement.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repo;
    private final UserAuthorization auth;

    public UserService(UserRepository repo, UserAuthorization auth){
        this.repo = repo;
        this.auth = auth;
    }
    //We'll use validation of users as a class to comply with SRP

    public List<UserDTO> findAllUsers(Long requestingUser){//Used to validate the user role. 
        User user = getUser(requestingUser);
        auth.requireAdmin(user);//Throws ex if not admin. Only admin feature.

        return repo.findAll().stream().map(u -> new UserDTO(u.getId(), u.getName(), u.getRole())).toList();
    }

    public UserDTO findUser(Long requestingUser, Long targetId){
        User user = getUser(requestingUser);

        auth.requireCanViewUser(user, targetId); // Check authorization (admins pass, same user passes, others fail)

        return toDTO(getUser(targetId));
    }

    public UserDTO createUser(CreateUserDTO newUser){
        validateCreateUser(newUser);

        User user = new User(newUser.getName(), newUser.getPassword(), newUser.getRole());
        repo.save(user);

        return toDTO(user);

    }

    public void deleteUser(Long requestingUser, Long targetId){
        validateId(requestingUser);

        User user = getUser(requestingUser);

        auth.canModifyUser(user, targetId); //Adtmin can delete any, standard can only change themselves. 
        repo.delete(targetId);
    }

    public void updatePassword(Long requestingUser, Long targetId, UpdatePasswordDTO pwDTO){//Just the id of user to be changed
        validateId(requestingUser);

        if(pwDTO == null){
            throw new IllegalArgumentException("The update data cannot be null");
        }

        if(pwDTO.getCurrentPassword() == null){
            throw new IllegalArgumentException("Current password is required");
        }

        User user = getUser(requestingUser);

        if(!requestingUser.equals(targetId)) {
            throw new UnauthorizedException("You can only change your own password");
        }
        
        if(!user.getPassword().equals(pwDTO.getCurrentPassword())){
            throw new UnauthorizedException("Current password is incorrect, cannot update");
        }

        if(pwDTO.getNewPassword() == null || pwDTO.getNewPassword().length() < 8){
            throw new IllegalArgumentException("New password must be at least 8 characters long");
        }

        user.setPassword(pwDTO.getNewPassword());
        repo.save(user);//The changes are not saved if using a DB, best to practice it from now even though we're working from memory.
    } 

    public UserDTO updateUserInfo(Long requiringId, Long targetId, UpdateUserDTO dto){
        User requestUser = getUser(requiringId);
        auth.requireCanModifyUser(requestUser, targetId);

        validateUpdate(dto);

        User targetUser = getUser(targetId);
        if(dto.getName() != null){
            targetUser.setName(dto.getName());
        }

        if(dto.getRole() != null){
            auth.requireAdmin(requestUser); //Only admin can change roles
            targetUser.setRole(dto.getRole());
        }

        repo.save(targetUser);
        return toDTO(targetUser);

    }

    //Helper methods
    private User getUser(Long id){
        validateId(id);
        return repo.findById(id).orElseThrow(() -> new UserNotfoundException("The user with id " + id + " not found"));
    }

    private void validateId(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("The id cannot be null");
        }
    }

    private UserDTO toDTO(User user){
        return new UserDTO(user.getId(), user.getName(), user.getRole());
    }

    private void validateCreateUser(CreateUserDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("User data cannot be null");
        }
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (dto.getPassword() == null || dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        if (dto.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }
    }

    private void validateUpdate(UpdateUserDTO dto) {
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
}
