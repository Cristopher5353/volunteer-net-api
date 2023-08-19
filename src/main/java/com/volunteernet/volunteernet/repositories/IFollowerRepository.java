package com.volunteernet.volunteernet.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.volunteernet.volunteernet.models.Follower;

public interface IFollowerRepository extends JpaRepository<Follower, Integer> {
    @Query("SELECT f FROM Follower f WHERE f.following.id = :followingId AND f.follower.id = :followerId")
    Follower findByFollowingIdAndFollowerId(int followingId, int followerId);

    @Query("SELECT f FROM Follower f WHERE f.following.id = :followingId")
    List<Follower> findAllByFollowingId(int followingId);
}
