package com.sebastian.usermanagement.service;

import org.springframework.stereotype.Component;

import com.sebastian.usermanagement.exception.UnauthorizedException;
import com.sebastian.usermanagement.model.User;

@Component
public class UserAuthorization {
    public void requireAdmin(User user){
        if(!user.isAdmin()){
            throw new UnauthorizedException("Admin access required");
        }
    }

    public void requireCanViewUser(User requestingUser, Long targetId){
        if(!requestingUser.canView(targetId)){
            throw new UnauthorizedException("Cannot see this user");
        }
    }

    public void requireCanModifyUser(User requestingUser, Long targetId){
        if(!requestingUser.canModify(targetId)){
            throw new UnauthorizedException("Cannot modify this user");
        }
    }

}
