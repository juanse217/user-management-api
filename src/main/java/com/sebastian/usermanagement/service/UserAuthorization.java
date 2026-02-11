package com.sebastian.usermanagement.service;

import org.springframework.stereotype.Component;

import com.sebastian.usermanagement.exception.UnauthorizedException;
import com.sebastian.usermanagement.model.Role;
import com.sebastian.usermanagement.model.User;

@Component
public class UserAuthorization {
    private boolean isAdmin(User requestingUser){ //private because my serivce or other layers don't need to know if a user is an admin, they just require an admin and that's done with the requireAdmin method. 
        return requestingUser.getRole() == Role.ADMIN;
    }

    public boolean canViewUser(User requestingUser, Long targetId){//Kept public because they're useful in case we need to check conditionals (we're returning a dto and we can add more info if the user canViewUser like emails or phone numebr, more sensitive info)
        return requestingUser.getId().equals(targetId) || isAdmin(requestingUser);
    }

    public boolean canModifyUser(User requestingUser, Long targetId){
        return requestingUser.getId().equals(targetId) || isAdmin(requestingUser);
    }

    public void requireAdmin(User user){
        if(!isAdmin(user)){
            throw new UnauthorizedException("Admin access required");
        }
    }

    public void requireCanViewUser(User requestingUser, Long targetId){
        if(!canViewUser(requestingUser, targetId)){
            throw new UnauthorizedException("Cannot see this user");
        }
    }

    public void requireCanModifyUser(User requestingUser, Long targetId){
        if(!canModifyUser(requestingUser, targetId)){
            throw new UnauthorizedException("Cannot modify this user");
        }
    }

}
