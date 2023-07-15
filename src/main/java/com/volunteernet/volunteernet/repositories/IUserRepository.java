package com.volunteernet.volunteernet.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.volunteernet.volunteernet.models.User;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
