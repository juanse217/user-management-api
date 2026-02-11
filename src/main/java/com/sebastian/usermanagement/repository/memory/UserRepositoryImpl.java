package com.sebastian.usermanagement.repository.memory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        return Collections.unmodifiableCollection(users.values());
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
