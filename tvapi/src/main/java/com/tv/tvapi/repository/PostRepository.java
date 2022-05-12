package com.tv.tvapi.repository;

import com.tv.tvapi.model.Post;
import com.tv.tvapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser(User user);

    Optional<Post> findByIdAndUser_Id(Long postId, Long userId);

    @Query(
            nativeQuery = true,
            value = "SELECT p.* FROM post p JOIN user u " +
                    "ON p.user_id = u.id " +
                    "WHERE p.user_id=:userId AND  p.active =:active AND p.status =:status AND p.type =1 "
    )
    List<Post> getUserPostNative(@Param("userId") Long userId,
                                 @Param("active") Integer active,
                                 @Param("status") Integer status,
                                 Pageable pageable
    );

    @Query(
            nativeQuery = true,
            value = "SELECT p.* FROM post p JOIN user u " +
                    "ON p.user_id = u.id " +
                    "WHERE p.user_id=:userId " +
                    "AND (:status IS NULL OR p.status =:status) " +
                    "AND (:type IS NULL OR p.type =:type) " +
                    "AND (:hour IS NULL OR p.create_date > DATE_SUB(NOW(),INTERVAL :hour HOUR) )"
    )
    List<Post> getUserPostsNative(@Param("userId") Long userId,
                                  @Param("status") Integer status,
                                  @Param("hour") Integer hour,
                                  @Param("type") Integer type,
                                  Pageable pageable
    );

    @Query(
            nativeQuery = true,
            value = "SELECT p.* FROM post p \n" +
                    "JOIN user u ON p.user_id = u.id\n" +
                    "WHERE  p.user_id \n" +
                    "IN (SELECT f.user_id FROM user u \n" +
                    "JOIN follow f ON u.id = f.follower_id \n" +
                    "WHERE u.id = :userId AND f.status =1 \n" +
                    "UNION SELECT :userId) \n" +
                    "AND p.create_date > SUBDATE(NOW(),INTERVAL 1000 HOUR)\n" +
                    "AND p.type = 0"
    )
    List<Post> getUserStoriesNative(@Param("userId") Long userId, Pageable pageable);


    @Query(
            nativeQuery = true,
            value = "SELECT * FROM post p\n" +
                    "WHERE p.type = 1"
    )
    List<Post> getPostsNative(Pageable pageable);


}
