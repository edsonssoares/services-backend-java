package com.soulcode.services.Services;

import com.soulcode.services.Models.User;
import com.soulcode.services.Repositories.UserRepository;
import com.soulcode.services.Security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @CachePut(value = "authCache", key = "#username")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(username); // filtro por email
        if (user.isEmpty()) { // ou use !user.isPresent()
            throw new UsernameNotFoundException("Usuário não Encontrado");
        }

        return new AuthUserDetail(user.get().getLogin(), user.get().getPassword());
    }
}

/*
 * O propósito do UserDetailService é carregar de alguma fonte de dados
 * o usuário e criar uma instância de AuthUserDetail, conhecida pelo Spring.
 **/