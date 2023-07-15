package com.volunteernet.volunteernet.services.ServiceImpl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.dto.user.UserSaveDto;
import com.volunteernet.volunteernet.exceptions.EmailAlreadyExistsException;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IRoleRepository;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.IUserService;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserSaveDto userSaveDto) {
        Optional<User> findUserByEmail = userRepository.findByEmail(userSaveDto.getEmail());

        if(findUserByEmail.isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        User newUser = new User();
        newUser.setUsername(userSaveDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userSaveDto.getPassword()));
        newUser.setEmail(userSaveDto.getEmail());
        newUser.setRole(rolRepository.findById(userSaveDto.getRole()).get());

        userRepository.save(newUser);
    }
}
