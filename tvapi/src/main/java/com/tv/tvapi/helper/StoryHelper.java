package com.tv.tvapi.helper;

import com.tv.tvapi.controller.StoryUploadRequest;
import com.tv.tvapi.exception.FileNotFoundException;
import com.tv.tvapi.model.*;
import com.tv.tvapi.request.BaseParamRequest;
import com.tv.tvapi.response.BaseResponse;
import com.tv.tvapi.response.StoryInfoResponse;
import com.tv.tvapi.response.UserInfoResponse;
import com.tv.tvapi.response.UserStoryResponse;
import com.tv.tvapi.service.*;
import com.tv.tvapi.utilities.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StoryHelper {

    private final StoryService storyService;
    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final StoryViewService storyViewService;
    private final ModelMapper modelMapper;
    private final FollowService followService;


    public ResponseEntity<?> uploadStory(StoryUploadRequest storyUploadRequest) {
        Long storyId = storyUploadRequest.getId();
        String description = storyUploadRequest.getDescription();
        Long coverId = storyUploadRequest.getCover();

        FileUpload coverFile;
        User currentUser = userService.getCurrentUser();
        if (coverId == null || !ValidationUtil.isNumeric(String.valueOf(coverId))
                || (coverFile = fileStorageService.getFileUpload(coverId, currentUser)) == null) {
            return BaseResponse.badRequest("Failed to upload story");
        }
        //check image or video exist in database
        try {
            fileStorageService.getFileFromStorage(coverFile.getName());
        } catch (FileNotFoundException e) {
            return BaseResponse.badRequest(e.getMessage());
        }
        Story story;
        if (storyId != null && storyService.existById(storyId)) {
            story = storyService.getById(storyId);
        } else {
            story = new Story();
            story.setUser(currentUser);
        }
        story.setDescription(description);
        story.setCover(coverFile);
        story = storyService.save(story);
        StoryInfoResponse storyResponse = modelMapper.map(story, StoryInfoResponse.class);
        return BaseResponse.success(storyResponse, "Upload story success!");

    }

    public ResponseEntity<?> getFriendStories(Map<String, String> params) {

        BaseParamRequest baseParamRequest = new BaseParamRequest(params);
        Pageable pageable = PageRequest
                .of(baseParamRequest.getPage(), baseParamRequest.getPageSize(),
                        Sort.by(Sort.Direction.fromString(baseParamRequest.getDirection()), baseParamRequest.getSortBy()));

        List<Object> data = new LinkedList<>();

        User currentUser = userService.getCurrentUser();
        //get current user stories
        List<Story> myStories = storyService.findByUserAndCreateDate(currentUser.getId(), 1, 1, null);
        List<StoryInfoResponse> currentUserStories = myStories.stream()
                .map(s -> modelMapper.map(s, StoryInfoResponse.class))
                .collect(Collectors.toList());
        data.add(new UserStoryResponse(modelMapper.map(currentUser, UserInfoResponse.class), currentUserStories));
        //get following friends stories
        List<Follow> follows = followService.getFollowingUsers(currentUser, 1);
        for (Follow f :
                follows) {
            User uploadByUser = f.getUser();
            List<Story> stories = storyService.findByUserAndCreateDate(uploadByUser.getId(), 1, 1, pageable);
            UserInfoResponse followingUserInfo = modelMapper.map(uploadByUser, UserInfoResponse.class);
            //display all stories includes viewed
            if (!stories.isEmpty() && stories.size() > 1) {
                AtomicInteger totalViewed = new AtomicInteger();
                List<StoryInfoResponse> storyResponseList = stories.stream()
                        //check story is not viewed
//                        .filter(story -> (!storyViewService.isViewed(currentUser, story)))
                        .map(story -> {
                            StoryInfoResponse storyInfoResponse = modelMapper.map(story, StoryInfoResponse.class);
                            boolean viewed = storyViewService.isViewed(currentUser, story);
                            if (viewed) {
                                totalViewed.getAndIncrement();
                            }
                            storyInfoResponse.setIsViewed(viewed);
                            return storyInfoResponse;
                        })
                        .collect(Collectors.toList());
                if (totalViewed.get() != stories.size()) {
                    data.add(new UserStoryResponse(followingUserInfo, storyResponseList));
                }
            } else if (stories.size() == 1 && !storyViewService.isViewed(currentUser, stories.get(0))) {
                StoryInfoResponse storyInfoResponse = modelMapper.map(stories.get(0), StoryInfoResponse.class);
                storyInfoResponse.setIsViewed(false);
                data.add(new UserStoryResponse(followingUserInfo, Collections.singletonList(
                        storyInfoResponse

                )));
            }
        }
        return BaseResponse.success(data, "get stories success!");
    }


    public ResponseEntity<?> getStory(Long storyId) {
        Story story = storyService.getById(storyId);
        User currentUser = userService.getCurrentUser();
        if (story == null)
            return BaseResponse.badRequest("Could not find story with id: " + storyId);

        StoryView storyView;
        if ((storyView = storyViewService.getByViewerAndStory(currentUser, story)) == null) {
            storyView = new StoryView();
            storyView.setCount(1);
        } else {
            int c = storyView.getCount();
            storyView.setCount(++c);
        }

        storyView.setStory(story);
        storyView.setViewer(currentUser);
        storyViewService.save(storyView);
        StoryInfoResponse data = modelMapper.map(story, StoryInfoResponse.class);

        return BaseResponse.success(data, "get story with id: " + storyId + " success");
    }
}
