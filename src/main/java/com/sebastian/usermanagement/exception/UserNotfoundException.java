package com.sebastian.usermanagement.exception;

public class UserNotfoundException extends RuntimeException{
    public UserNotfoundException(String msg){
        super(msg);
    }
}
