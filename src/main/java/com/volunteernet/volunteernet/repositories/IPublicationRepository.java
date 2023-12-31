package com.volunteernet.volunteernet.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.volunteernet.volunteernet.models.Publication;

public interface IPublicationRepository extends JpaRepository<Publication, Integer>{
    @Query("SELECT p FROM Publication p WHERE p.user.id != :userId " +
           "ORDER BY CASE WHEN p.user.id IN (SELECT f.following.id FROM Follower f WHERE f.follower.id = :userId) THEN 1 ELSE 0 END DESC, p.createdAt DESC")
    Page<Publication> findAllByUserIdNotEqual(int userId, Pageable pageable);

    @Query("SELECT p FROM Publication p WHERE p.user.id = :userId ORDER BY p.createdAt DESC")
    List<Publication> findAllByUserId(int userId, Pageable pageable);
}
