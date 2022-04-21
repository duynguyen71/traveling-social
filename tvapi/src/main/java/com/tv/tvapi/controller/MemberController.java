package com.tv.tvapi.controller;

import com.tv.tvapi.helper.PostHelper;
import com.tv.tvapi.helper.StoryHelper;
import com.tv.tvapi.helper.UserHelper;
import com.tv.tvapi.request.CreatePostRequest;
import com.tv.tvapi.request.StoryCommentRequest;
import com.tv.tvapi.request.StoryUploadRequest;
import com.tv.tvapi.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final UserHelper userHelper;
    private final StoryHelper storyHelper;
    private final PostHelper postHelper;

    @GetMapping("/users/me/files/{name}")
    public ResponseEntity<?> findFile(@PathVariable("name") String fileName) {
        return userHelper.getImage(fileName);
    }

    @GetMapping("/users/searching")
    public ResponseEntity<?> search(@RequestParam Map<String, String> params) {
        return userHelper.searchingUsers(params);
    }

    @PostMapping("/users/me/avt")
    public ResponseEntity<?> uploadAvt(@RequestParam("file") MultipartFile file) {
        return userHelper.updateAvt(file);
    }

    @GetMapping("/users/me")
    public ResponseEntity<?> getCurrentUserInfo() {
        return userHelper.getCurrentUserDetail();
    }

    @PostMapping("/users/me")
    public ResponseEntity<?> updateCurrentUserInfo(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        return userHelper.updateCurrentUserInfo(userUpdateRequest);
    }

    @GetMapping("/users/me/following")
    public ResponseEntity<?> getFollowingUsers() {
        return userHelper.getFollowingUsers();
    }

    @GetMapping("/users/me/following/{userId}")
    public ResponseEntity<?> followRequest(@PathVariable("userId") Long userId) {
        return userHelper.followUser(userId);
    }

    @GetMapping("/users/me/posts")
    public ResponseEntity<?> getCurrentUserPosts(@RequestParam Map<String, String> params) {
        return postHelper.getCurrentUserPosts(params);
    }

    @GetMapping("/users/me/posts/{postId}/comments")
    public ResponseEntity<?> getPostComments(@PathVariable("postId") Long postId) {
        return postHelper.getPostComments(postId);
    }

    @PostMapping("/users/me/posts")
    public ResponseEntity<?> createPost(@RequestBody @Valid CreatePostRequest createPostRequest) {
        return postHelper.createPost(createPostRequest);
    }

    @GetMapping("/users/me/followers")
    public ResponseEntity<?> getFollowers() {
        return userHelper.getFollowers();
    }


    @GetMapping("/users/me/unfollow/{id}")
    public ResponseEntity<?> unFollow(@PathVariable("id") Long id) {
        return userHelper.unFollowUser(id);
    }

    @GetMapping("/users/me/stories")
    public ResponseEntity<?> getFriendStories(@RequestParam Map<String, String> params) {
        return storyHelper.getFriendStories(params);
    }

    @PostMapping("/users/me/stories")
    public ResponseEntity<?> uploadStory(@RequestBody
                                         @Valid StoryUploadRequest storyUploadRequest) {
        return storyHelper.uploadStory(storyUploadRequest);
    }

    @GetMapping("/stories/{storyId}")
    public ResponseEntity<?> viewStory(@PathVariable("storyId") Long storyId) {
        return storyHelper.getStory(storyId);
    }

    @PostMapping("/stories/{storyId}/comments")
    public ResponseEntity<?> commentStory(@PathVariable("storyId") Long storyId, @RequestBody @Valid StoryCommentRequest storyCommentRequest) {
        return userHelper.commentStory(storyId, storyCommentRequest);
    }

}
