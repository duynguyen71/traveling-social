package com.tv.tvapi.repository;

import com.tv.tvapi.model.Follow;
import com.tv.tvapi.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {


    int countByUserAndActive(User user,int active);

    int countByFollowerAndActive(User follower,int active);

    List<Follow> findByUser(User user);

    List<Follow> findByFollowerAndActive(User follower, int active);

    Optional<Follow> findByUserAndFollowerAndActive(User user, User follower,int active);
}
