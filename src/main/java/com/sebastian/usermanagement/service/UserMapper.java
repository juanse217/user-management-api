package com.sebastian.usermanagement.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sebastian.usermanagement.dto.UserDTO;
import com.sebastian.usermanagement.model.User;

@Component
public class UserMapper {
    
    public UserDTO toDTO(User user){
        return new UserDTO(user.getId(), user.getName(), user.getRole());
    }

    public List<UserDTO> toDTOList(Collection<User> list){

        return list.stream().map(u -> new UserDTO(u.getId(), u.getName(), u.getRole())).toList();
    }
}
