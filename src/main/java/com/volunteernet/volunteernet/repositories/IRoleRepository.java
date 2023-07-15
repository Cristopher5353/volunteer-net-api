package com.volunteernet.volunteernet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.volunteernet.volunteernet.models.Role;

public interface IRoleRepository extends JpaRepository<Role, Integer>{
    
}
