package com.sebastian.usermanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sebastian.usermanagement.dto.CreateUserDTO;
import com.sebastian.usermanagement.dto.UpdatePasswordDTO;
import com.sebastian.usermanagement.dto.UpdateUserDTO;
import com.sebastian.usermanagement.dto.UserDTO;
import com.sebastian.usermanagement.exception.UnauthorizedException;
import com.sebastian.usermanagement.exception.UserNotFoundException;
import com.sebastian.usermanagement.model.User;
import com.sebastian.usermanagement.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repo;
    private final UserAuthorization auth;
    private final UserValidator validator;
    private final UserMapper mapper; 

    public UserService(UserRepository repo, UserAuthorization auth, UserValidator validator, UserMapper mapper){
        this.repo = repo;
        this.auth = auth;
        this.validator = validator;
        this.mapper = mapper;
    }
    //We'll use validation of users as a class to comply with SRP

    public List<UserDTO> findAllUsers(Long requestingUser){//Used to validate the user role. 
        User user = getUser(requestingUser);
        auth.requireAdmin(user);//Throws ex if not admin. Only admin feature.

        return mapper.toDTOList(repo.findAll());
    }

    public UserDTO findUser(Long requestingUser, Long targetId){
        User user = getUser(requestingUser);

        auth.requireCanViewUser(user, targetId); // Check authorization (admins pass, same user passes, others fail)

        return mapper.toDTO(getUser(targetId));
    }

    public UserDTO createUser(CreateUserDTO newUser){
        if(newUser == null){
            throw new IllegalArgumentException("User data cannot be null");
        }

        User user = new User(newUser.getName(), newUser.getPassword(), newUser.getRole());//Null checks in the constructor. 
        repo.save(user);

        return mapper.toDTO(user);

    }

    public void deleteUser(Long requestingUser, Long targetId){
        validator.validateId(requestingUser);

        User user = getUser(requestingUser);

        auth.requireCanModifyUser(user, targetId); //Admin can delete any, standard can only change themselves. 
        repo.delete(targetId);
    }

    public void updatePassword(Long requestingUser, Long targetId, UpdatePasswordDTO pwDTO){//Just the id of user to be changed
        validator.validateId(requestingUser);

        validator.validatePasswordUpdate(pwDTO); //checking nulls and pw length

        User user = getUser(requestingUser);

        if(!requestingUser.equals(targetId)) {
            throw new UnauthorizedException("You can only change your own password");
        }

        if(!user.getPassword().equals(pwDTO.getCurrentPassword())){
            throw new UnauthorizedException("Current password is incorrect, cannot update");
        }

        user.updatePassword(pwDTO.getNewPassword());
        repo.save(user);//The changes are not saved if using a DB, best to practice it from now even though we're working from memory.
    } 

    public UserDTO updateUserInfo(Long requiringId, Long targetId, UpdateUserDTO dto){
        User requestUser = getUser(requiringId);
        auth.requireCanModifyUser(requestUser, targetId);

        validator.validateUpdate(dto);

        User targetUser = getUser(targetId);
        if(dto.getName() != null){
            targetUser.updateName(dto.getName());
        }

        if(dto.getRole() != null){
            auth.requireAdmin(requestUser); //Only admin can change roles
            targetUser.updateRole(dto.getRole());
        }

        repo.save(targetUser);
        return mapper.toDTO(targetUser);

    }

    //Helper methods
    private User getUser(Long id){
        validator.validateId(id);
        return repo.findById(id).orElseThrow(() -> new UserNotFoundException("The user with id " + id + " not found"));
    }
}
