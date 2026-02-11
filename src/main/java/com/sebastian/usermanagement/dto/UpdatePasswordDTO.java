package com.sebastian.usermanagement.dto;

public class UpdatePasswordDTO {
    private String currentPassword;
    private String newPassword; 

    public UpdatePasswordDTO(){

    }
    
    //Testability constructor

    public UpdatePasswordDTO(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }



    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    
}
