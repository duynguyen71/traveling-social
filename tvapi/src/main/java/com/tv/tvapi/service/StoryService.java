package com.tv.tvapi.service;

import com.tv.tvapi.model.Story;
import com.tv.tvapi.repository.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryService {

    private final StoryRepository storyRepo;

    public boolean existById(Long id){
        return storyRepo.existsById(id);
    }

    public Story getById(Long id){
        return storyRepo.findById(id).orElse(null);
    }

    public Story save(Story story) {
        return storyRepo.saveAndFlush(story);
    }

    public List<Story> findByUserAndCreateDate(Long user_id, int day, int active, Pageable pageable){
        return storyRepo.findStoriesNative(user_id,active,day,pageable);
    }

    public Story getStoryLast24Hour(int active,Long storyId){
        return storyRepo.findStoryLast24HourNative(active,storyId).orElse(null);
    }
}
