package com.volunteernet.volunteernet.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.volunteernet.volunteernet.models.Publication;

public interface IPublicationRepository extends JpaRepository<Publication, Integer>{
    @Query("SELECT p FROM Publication p WHERE p.user.id != :userId")
    List<Publication> findByUserIdNotEqual(int userId);
}
