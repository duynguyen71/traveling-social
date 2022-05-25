package com.tv.tvapi.helper;

import com.tv.tvapi.model.*;
import com.tv.tvapi.request.AttachmentRequest;
import com.tv.tvapi.request.BaseParamRequest;
import com.tv.tvapi.request.CreatePostRequest;
import com.tv.tvapi.request.ContentUploadRequest;
import com.tv.tvapi.response.*;
import com.tv.tvapi.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
    private final PostReactionService postReactionService;
    private final PostCommentService postCommentService;

    /**
     * Lay ra cac bai post cua user hien tai
     *
     * @param params
     * @return
     */
    public ResponseEntity<?> getCurrentUserPosts(Map<String, String> params) {
        User currentUser = userService.getCurrentUser();
        BaseParamRequest baseParamRequest = new BaseParamRequest(params);

        Pageable pageable = baseParamRequest.toPageRequest();

        List<Post> posts = postService.getUserPosts(currentUser, baseParamRequest.getStatus(), pageable);
        List<PostResponse> rs = posts.stream()
                .map(post -> {
                    PostResponse postResponse = modelMapper.map(post, PostResponse.class);
                    //lay ra so luong reactions
                    Long reactionCount = postReactionService.countReactions(post);
                    postResponse.setReactionCount(reactionCount);
//
                    //get current user reaction on post
                    PostReaction postReaction = postReactionService.getByUser(currentUser, post, 1);
                    if (postReaction != null)
                        postResponse.setMyReaction(modelMapper.map(postReaction.getReaction(), ReactionResponse.class));
                    return postResponse;
                }).collect(Collectors.toList());

        return BaseResponse.success(rs, "get user id: " + currentUser.getId() + "\'s posts success!");
    }

    //    public ResponseEntity<?> createPost(CreatePostRequest createPostRequest) {
//        User currentUser = userService.getCurrentUser();
//        String postCaption = createPostRequest.getCaption();
//        Long postRequestId = createPostRequest.getId();
//        List<ContentUploadRequest> contents =
//                createPostRequest.getContents();
//        String messageResponse = "create post success";
//        Post post;
//        if (postRequestId != null && (post = postService.getById(postRequestId)) != null) {
//            messageResponse = "update post success";
//        } else {
//            post = new Post();
//            post.setUser(currentUser);
//        }
//        post.setCaption(postCaption);
//        post.setStatus(createPostRequest.getStatus());
//        post.setType(createPostRequest.getType());
//
//        post = postService.savePost(post);
//
//        for (int i = 0; i < contents.size(); i++) {
//            ContentUploadRequest postContentRequest = contents.get(i);
//            Long postContentId = postContentRequest.getId();
//            FileUpload fileUpload;
//            if ((fileUpload = fileStorageService.getById(postContentRequest.getAttachmentId())) != null) {
//                PostContent postContent;
//                Integer pos = postContentRequest.getPos();
//                if (postContentId != null &&
//                        (postContent = postService.getPostMediaFile(postContentId, post)) != null) {
//                } else {
//                    postContent = new PostContent();/
//                    postContent.setPost(post);
//                    postContent.setAttachment(fileUpload);
//                }
//                postContent.setActive(postContentRequest.getActive());
//                postContent.setCaption(postContentRequest.getCaption());
//                postContent.setPos(pos != null ? pos : i);
//                postService.savePostContent(postContent);
//            }
//        }
//        //map to post response
//        PostResponse postResponse = modelMapper.map(post, PostResponse.class);
//        postResponse.setUser(modelMapper.map(currentUser, UserInfoResponse.class));
//        //get contents
//        List<PostContent> postContents = postService.getPostContents(post);
//        postResponse.setContents(
//                postContents.stream()
//                        .map(postContent -> {
//                            PostContentResponse postContentResponse = modelMapper.map(postContent, PostContentResponse.class);
//                            FileUploadResponse map = modelMapper.map(postContent.getAttachment(), FileUploadResponse.class);
//                            postContentResponse.setAttachment(map);
//                            return postContentResponse;
//                        })
//                        .collect(Collectors.toList())
//        );
//
//
//        return BaseResponse.success(postResponse, messageResponse);
//    }
    public ResponseEntity<?> createPost(CreatePostRequest createPostRequest) {
        User currentUser = userService.getCurrentUser();
        String postCaption = createPostRequest.getCaption();
        Long postRequestId = createPostRequest.getId();
        List<ContentUploadRequest> contents =
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
        post.setStatus(1);
        post.setType(createPostRequest.getType());

        post = postService.savePostAndFlush(post);

        for (int i = 0; i < contents.size(); i++) {
            ContentUploadRequest contentRequest = contents.get(i);
            Long postContentId = contentRequest.getId();
            AttachmentRequest attachmentRequest = contentRequest.getAttachment();
            if (attachmentRequest != null) {
                FileUpload fileUpload;
                if (attachmentRequest.getId() != null) {
                    fileUpload = fileStorageService.getById(attachmentRequest.getId());
                } else if (attachmentRequest.getName() != null) {
                    fileUpload = fileStorageService.getByName(attachmentRequest.getName());
                } else {
                    continue;
                }
                if (fileUpload == null) {
                    continue;
                }
                PostContent postContent;
                Integer pos = contentRequest.getPos();
                if (postContentId != null &&
                        (postContent = postService.getPostMediaFile(postContentId, post)) != null) {
                } else {
                    postContent = new PostContent();
                    postContent.setPost(post);
                    postContent.setAttachment(fileUpload);
                }
                postContent.setActive(contentRequest.getActive());
                postContent.setCaption(contentRequest.getCaption());
                postContent.setStatus(1);
                postContent.setPos(pos != null ? pos : i);
                postService.savePostContent(postContent);
            }
        }
        //map to post response
        PostResponse postResponse = modelMapper.map(post, PostResponse.class);
        postResponse.setUser(modelMapper.map(currentUser, UserInfoResponse.class));
        //get contents
        List<PostContent> postContents = postService.getPostContents(post);
        postResponse.setContents(
                postContents.stream()
                        .map(postContent -> {
                            PostContentResponse postContentResponse = modelMapper.map(postContent, PostContentResponse.class);
                            FileUploadResponse map = modelMapper.map(postContent.getAttachment(), FileUploadResponse.class);
                            postContentResponse.setAttachment(map);
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

    public ResponseEntity<?> getStories(Map<String, String> params) {
        User currentUser = userService.getCurrentUser();
        BaseParamRequest baseParamRequest = new BaseParamRequest(params);

        Pageable pageable = baseParamRequest.toPageRequest();
        List<Post> posts = postService.getUserStories(currentUser, pageable);
        List<PostResponse> data = posts.stream()
                .map(post -> {
                    PostResponse postResponse = modelMapper.map(post, PostResponse.class);
                    List<PostContent> postContents = postService.getPostContents(post);
                    postResponse.setContents(
                            postContents.stream()
                                    .map(content -> modelMapper.map(content, PostContentResponse.class))
                                    .collect(Collectors.toList())
                    );
                    postResponse.setLikeCount(1);
                    return postResponse;
                })
                .collect(Collectors.toList());

        return BaseResponse.success(data, "get current user's stories success");
    }


    public ResponseEntity<?> getPosts(Map<String, String> params) {
        User currentUser = userService.getCurrentUser();
        BaseParamRequest baseParamRequest = new BaseParamRequest(params);

        Pageable pageable = baseParamRequest.toPageRequest();
        List<Post> posts = postService.getPosts(currentUser, pageable);

        List<PostResponse> data = posts.stream()
                .map(post -> {
                    PostResponse postResponse = modelMapper.map(post, PostResponse.class);
                    postResponse.setReactionCount(postReactionService.countReactions(post));
                    postResponse.setCommentCount(postCommentService.countAllComments(post));
                    //get current user reaction on post
                    PostReaction postReaction = postReactionService.getByUser(currentUser, post, 1);
                    if (postReaction != null)
                        postResponse.setMyReaction(modelMapper.map(postReaction.getReaction(), ReactionResponse.class));
                    //get current users comments on post
                    List<PostComment> myComments = postCommentService.getCurrentUserPostComments(post, currentUser, 1);
                    postResponse.setMyComments(
                            myComments.stream()
                                    .map(c -> {
                                        PropertyMap<PostComment, CommentResponse> clientPropertyMap = new PropertyMap<>() {
                                            @Override
                                            protected void configure() {
                                                skip(destination.getUser());
                                            }
                                        };
                                        if (modelMapper.getTypeMap(PostComment.class, CommentResponse.class) == null)
                                            modelMapper.addMappings(clientPropertyMap);
                                        CommentResponse commentResponse = modelMapper.map(c, CommentResponse.class);
                                        commentResponse.setReplyCount(postCommentService.countReply(c));
                                        return commentResponse;
                                    })
                                    .collect(Collectors.toList())
                    );
                    //lay ra noi dung bai post
                    List<PostContent> postContents = postService.getPostContents(post);
                    postResponse.setContents(
                            postContents.stream()
                                    .map(content -> {
                                        PostContentResponse postContentResponse = modelMapper.map(content, PostContentResponse.class);
                                        FileUploadResponse map = modelMapper.map(content.getAttachment(), FileUploadResponse.class);
                                        postContentResponse.setAttachment(map);
                                        return postContentResponse;
                                    })
                                    .collect(Collectors.toList())
                    );
                    return postResponse;
                })
                .collect(Collectors.toList());

        return BaseResponse.success(data, "get current user's post success");
    }


    public ResponseEntity<?> updateStatus(Long postId, Integer status) {
        Post post = postService.getCurrentUserPost(postId, userService.getCurrentUser());
        if (post != null) {
            post.setStatus(status);
            postService.save(post);
            return BaseResponse.success("update post " + post + " status success!");
        }
        return BaseResponse.badRequest("could not find post with id: " + postId);
    }
}
