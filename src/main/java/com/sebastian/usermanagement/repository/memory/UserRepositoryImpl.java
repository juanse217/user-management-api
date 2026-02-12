package com.sebastian.usermanagement.repository.memory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.sebastian.usermanagement.model.User;
import com.sebastian.usermanagement.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private Long idGenerator = 1L; //No need for static since there will be only one instance of my repository (managed by Spring context)
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User save(User user) {

        if(user.getId() == null){ //when not created, null by default
            user.setId(idGenerator++);
        }

        users.put(user.getId(), user);//Not returning this since returns the previous value associated to the key
        return user;
    }

    @Override
    public Collection<User> findAll() {
        // Return defensive copies. Model class is mutable, so they can acces an item and change it. Best to send copies. 
        return users.values().stream()
                .map(this::copyUser)
                .collect(Collectors.toList());
    }

    private User copyUser(User original) {
        User copy = new User(original.getName(), original.getPassword(), original.getRole());
        copy.setId(original.getId());
        return copy;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public void delete(Long id) {
        users.remove(id);
    }

    @Override
    public boolean exists(Long id) {
        return users.containsKey(id);
    }

}
