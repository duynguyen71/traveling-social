package com.tv.tvapi.service;

import com.tv.tvapi.model.Story;
import com.tv.tvapi.model.StoryView;
import com.tv.tvapi.model.User;
import com.tv.tvapi.repository.StoryViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoryViewService {

    private final StoryViewRepository storyViewRepo;

    public StoryView getByViewerAndStory(User viewer, Story story){
        return storyViewRepo.findByViewerAndStory(viewer,story).orElse(null);
    }

    public boolean isViewed(User viewer,Story story){
        return storyViewRepo.existsByViewerAndStory(viewer,story    );
    }

    public StoryView save(StoryView storyView) {
        return storyViewRepo.saveAndFlush(storyView);
    }
}
