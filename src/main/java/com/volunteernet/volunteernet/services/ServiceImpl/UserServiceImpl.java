package com.volunteernet.volunteernet.services.ServiceImpl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.volunteernet.volunteernet.dto.user.UserSaveDto;
import com.volunteernet.volunteernet.exceptions.EmailAlreadyExistsException;
import com.volunteernet.volunteernet.exceptions.RoleNotExistsException;
import com.volunteernet.volunteernet.models.Role;
import com.volunteernet.volunteernet.models.User;
import com.volunteernet.volunteernet.repositories.IRoleRepository;
import com.volunteernet.volunteernet.repositories.IUserRepository;
import com.volunteernet.volunteernet.services.IServices.IUserService;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserSaveDto userSaveDto) {
        Optional<User> findUserByEmail = userRepository.findByEmail(userSaveDto.getEmail());
        Optional<Role> findRoleById = roleRepository.findById(userSaveDto.getRole());

        if(findUserByEmail.isPresent()) {
            throw new EmailAlreadyExistsException();
        }

        if(!findRoleById.isPresent()) {
            throw new RoleNotExistsException();
        }

        User newUser = new User();
        newUser.setUsername(userSaveDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userSaveDto.getPassword()));
        newUser.setEmail(userSaveDto.getEmail());
        newUser.setDescription(userSaveDto.getDescription().trim().length() == 0 ?null :userSaveDto.getDescription());
        newUser.setRole(findRoleById.get());

        userRepository.save(newUser);
    }
}
