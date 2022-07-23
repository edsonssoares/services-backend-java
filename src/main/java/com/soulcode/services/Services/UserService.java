package com.soulcode.services.Services;

import com.soulcode.services.Models.User;
import com.soulcode.services.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Cacheable("usersCache")
    public List<User> listar() {
        return userRepository.findAll();
    }

    @CachePut(value = "usersCache", key = "#user.idUser")
    public User cadastrar(User user) {
        return userRepository.save(user);
    }

}
