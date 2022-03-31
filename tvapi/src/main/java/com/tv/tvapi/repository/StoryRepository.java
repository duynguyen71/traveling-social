package com.tv.tvapi.repository;

import com.tv.tvapi.model.Story;
import com.tv.tvapi.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {


    @Query(
            nativeQuery = true,
            value = "SELECT s.* FROM story s WHERE s.create_date >= now() - INTERVAL :day DAY AND s.active = :active AND user_id = :userId"
    )
    List<Story> findStoriesNative(@Param("userId") Long userId, @Param("active") int active, @Param("day") int day, Pageable pageable);

    @Query(
            nativeQuery = true,
            value = "SELECT s.* FROM story s WHERE s.create_date >= now() - INTERVAL 1 DAY  " +
                    "AND s.active = :active " +
                    "AND s.id = :storyId"
    )
    Optional<Story> findStoryLast24HourNative(@Param("active") int active,
                                              @Param("storyId") Long storyId);


}
