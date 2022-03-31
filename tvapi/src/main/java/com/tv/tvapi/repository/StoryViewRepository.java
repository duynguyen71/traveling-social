package com.tv.tvapi.repository;

import com.tv.tvapi.model.Story;
import com.tv.tvapi.model.StoryView;
import com.tv.tvapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoryViewRepository extends JpaRepository<StoryView, Long> {

    Optional<StoryView> findByViewerAndStory(User viewer, Story story);

    boolean existsByViewerAndStory(User viewer, Story story);


}
