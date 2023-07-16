package com.volunteernet.volunteernet.security;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IUserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userByEmail = userRepository.findByEmail(username);

        if (!userByEmail.isPresent()) {
            throw new UsernameNotFoundException("Correo no existe en el sistema");
        }

        return new UserDetailService(userByEmail.get());
    }

}
