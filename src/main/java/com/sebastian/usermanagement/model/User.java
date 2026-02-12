package com.sebastian.usermanagement.model;

public class User {
    private Long id;
    private String name;
    private String password;
    private Role role;

    // Jackson needs this
    public User() {
    }

    // Constructor for creating new users (service layer uses this)
    public User(String name, String password, Role role) {
        validateName(name);
        validatePassword(password);
        validateRole(role);
        this.name = name;
        this.password = password;
        this.role = role;
    }

    // Behavior: Update user's name
    public void updateName(String newName) {
        validateName(newName);
        this.name = newName;
    }

    // Behavior: Update user's password
    public void updatePassword(String newPassword) {
        validatePassword(newPassword);
        this.password = newPassword;
    }

    // Behavior: Update user's role
    public void updateRole(Role newRole) {
        validateRole(newRole);
        this.role = newRole;
    }

    // Behavior: Check if user has admin privileges
    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    // Behavior: Check if this user can view another user's data
    public boolean canView(Long targetUserId) {
        return this.id.equals(targetUserId) || isAdmin();
    }

    // Behavior: Check if this user can modify another user's data
    public boolean canModify(Long targetUserId) {
        return this.id.equals(targetUserId) || isAdmin();
    }

    // Repository uses this to assign ID after creation
    public void setId(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("ID cannot be changed once set");
        }
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        this.id = id;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    // Private validation methods - encapsulated within the domain model
    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Name cannot exceed 100 characters");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
    }

    private void validateRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot is required");
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        if (id == null) {
            return other.id == null;
        }
        return id.equals(other.id);
    }
}