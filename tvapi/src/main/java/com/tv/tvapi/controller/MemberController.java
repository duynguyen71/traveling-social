package com.tv.tvapi.controller;

import com.tv.tvapi.helper.CommentHelper;
import com.tv.tvapi.helper.FileUploadHelper;
import com.tv.tvapi.helper.PostHelper;
import com.tv.tvapi.helper.UserHelper;
import com.tv.tvapi.request.*;
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
    private final PostHelper postHelper;
    private final FileUploadHelper fileUploadHelper;
    private final CommentHelper commentHelper;

    @GetMapping("/users/me/files/{name}")
    public ResponseEntity<?> findFile(@PathVariable("name") String fileName) {
        return userHelper.getImage(fileName);
    }

    @PostMapping("/users/me/files")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        return fileUploadHelper.uploadFile(file);
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
        return postHelper.getCurrentUserStories(params);
    }

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<?> commentPost(@PathVariable("id") Long id, @RequestBody PostCommentRequest postCommentRequest) {
        return commentHelper.commentPost(id, postCommentRequest);
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<?> getPostComments(@PathVariable("id") Long postId, @RequestParam Map<String, String> params) {
        return commentHelper.getPostComments(postId, params);
    }

    @GetMapping("/comments/{commentId}")
    public ResponseEntity<?> getComment(@PathVariable("commentId") Long commentId, @RequestParam Map<String, String> params) {
        return commentHelper.getComment(commentId, params);
    }


}