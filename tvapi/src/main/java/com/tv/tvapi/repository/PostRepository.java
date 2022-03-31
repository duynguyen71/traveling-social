package com.tv.tvapi.repository;

import com.tv.tvapi.model.Post;
import com.tv.tvapi.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);

    @Query(
            nativeQuery = true,
            value = "SELECT p.* FROM post p JOIN user u " +
                    "ON p.user_id = u.id " +
                    "WHERE p.user_id=:userId AND  p.active =:active AND p.status =:status "
    )
    List<Post> getUserPostNative(@Param("userId") Long userId,
                                 @Param("active") Integer active,
                                 @Param("status") Integer status,
                                 Pageable pageable
    );


}
