package com.volunteernet.volunteernet.util.handler.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IUserRepository;

@Component
public class AuthenticatedUser {
    @Autowired
    private static IUserRepository userRepository;

    public static User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return userRepository.findByUsername(username).get();
    }
}