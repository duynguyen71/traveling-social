package com.tv.tvapi.helper;

import com.tv.tvapi.model.*;
import com.tv.tvapi.request.BaseParamRequest;
import com.tv.tvapi.request.CreatePostRequest;
import com.tv.tvapi.request.ContentUploadRequest;
import com.tv.tvapi.response.*;
import com.tv.tvapi.service.FileStorageService;
import com.tv.tvapi.service.PostService;
import com.tv.tvapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("PostHelper")
@RequiredArgsConstructor
public class PostHelper {

    private final UserService userService;
    private final PostService postService;
    private final ModelMapper modelMapper;
    private final FileStorageService fileStorageService;

    public ResponseEntity<?> getCurrentUserPosts(Map<String, String> params) {
        User currentUser = userService.getCurrentUser();
        BaseParamRequest baseParamRequest = new BaseParamRequest(params);
        Pageable pageable = baseParamRequest.toPageRequest();
        List<Post> posts = postService.getUserPost(currentUser, pageable, 1, 1);

        List<PostResponse> data = posts.stream()
                .map(post -> {
                    Integer likes = postService.countPostLike(post);
                    PostResponse postResponse = modelMapper.map(post, PostResponse.class);
                    List<PostContent> postContents = postService.getPostContents(post);
                    postResponse.setContents(
                            postContents.stream()
                                    .map(content -> modelMapper.map(content, PostContentResponse.class))
                                    .collect(Collectors.toList())
                    );
                    postResponse.setLikeCounts(likes);
                    return postResponse;
                })
                .collect(Collectors.toList());

        return BaseResponse.success(data, "get current user's post success");
    }

    public ResponseEntity<?> createPost(CreatePostRequest createPostRequest) {
        User currentUser = userService.getCurrentUser();
        String postCaption = createPostRequest.getCaption();
        Long postRequestId = createPostRequest.getId();
        List<ContentUploadRequest> mediaFiles =
                createPostRequest.getContents();
        String messageResponse = "create post success";
        Post post;
        if (postRequestId != null && (post = postService.getById(postRequestId)) != null) {
            messageResponse = "update post success";
        } else {
            post = new Post();
            post.setUser(currentUser);
        }
        post.setCaption(postCaption);
        post.setStatus(createPostRequest.getStatus());
        post.setActive(createPostRequest.getActive());
        post = postService.savePost(post);

        for (int i = 0; i < mediaFiles.size(); i++) {
            ContentUploadRequest postContentRequest = mediaFiles.get(i);
            Long postContentId = postContentRequest.getId();
            //
            FileUpload fileUpload =
                    fileStorageService.getFileFromDbActive(postContentRequest.getFileId(), currentUser, 1);
            if (fileUpload != null && fileStorageService.getFileFromStorage(fileUpload.getName()) != null) {
                PostContent postContent;
                Integer pos = postContentRequest.getPos();
                if (postContentId != null &&
                        (postContent = postService.getPostMediaFile(postContentId, post)) != null) {
                } else {
                    postContent = new PostContent();
                    postContent.setPost(post);
                    postContent.setFile(fileUpload);
                }
                postContent.setCaption(postContentRequest.getCaption());
                postContent.setPos(pos != null ? pos : i);

                postService.savePostContent(postContent);
            }
        }

        //map to post response
        PostResponse postResponse = modelMapper.map(post, PostResponse.class);
        postResponse.setUploadBy(modelMapper.map(currentUser, UserInfoResponse.class));
        //get contents
        List<PostContent> postContents = postService.getPostContents(post);
        postResponse.setContents(
                postContents.stream()
                        .map(postContent -> {
                            PostContentResponse postContentResponse = modelMapper.map(postContent, PostContentResponse.class);
                            FileUploadResponse map = modelMapper.map(postContent.getFile(), FileUploadResponse.class);
                            postContentResponse.setFile(map);
                            return postContentResponse;
                        })
                        .collect(Collectors.toList())
        );


        return BaseResponse.success(postResponse, messageResponse);
    }

    public ResponseEntity<?> getPostComments(Long postId) {

        List<PostComment> postComments = postService.getPostComments(postService.getById(postId));
        List<CommentResponse> data = postComments.stream()
                .map(comment -> {
                    CommentResponse commentResponse = modelMapper.map(comment, CommentResponse.class);
                    return commentResponse;
                })
                .collect(Collectors.toList());
        return BaseResponse.success(data, "Get post comments with post id: " + postId + " success!");
    }
}
