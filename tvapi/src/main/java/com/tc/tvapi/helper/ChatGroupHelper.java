package com.tc.tvapi.helper;

import com.tc.tvapi.model.Message;
import com.tc.tvapi.request.ChatGroupRequest;
import com.tc.tvapi.response.BaseResponse;
import com.tc.tvapi.response.BaseUserResponse;
import com.tc.tvapi.response.ChatGroupResponse;
import com.tc.tvapi.response.MessageResponse;
import com.tc.tvapi.service.MessageService;
import com.tc.tvapi.model.ChatGroup;
import com.tc.tvapi.model.ChatGroupUser;
import com.tc.tvapi.model.User;
import com.tc.tvapi.service.ChatGroupService;
import com.tc.tvapi.service.ChatGroupUserService;
import com.tc.tvapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component("ChatGroupHelper")
public class ChatGroupHelper {

    private final ChatGroupService service;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final MessageService messageService;
    private final ChatGroupUserService chatUserService;
    private final ChatGroupUserService chatGroupUserService;


    public ResponseEntity<?> getChatGroups(Map<String, String> param) {
        List<ChatGroup> groups = service.getGroups(userService.getCurrentUser(), 1);
        List<ChatGroupResponse> chatGroupResponses = groups.stream()
                .map(g -> {
                    ChatGroupResponse chatGroupResponse = modelMapper.map(g, ChatGroupResponse.class);
                    //get last message
                    Message lastMessage = messageService.getLastMessage(g, 1);
                    if (lastMessage != null) {
                        chatGroupResponse.setLastMessage(modelMapper.map(lastMessage, MessageResponse.class));
                    }
                    //get users
                    List<ChatGroupUser> chatGroupUsers = chatUserService.getChatUsers(g, 1);
                    Set<BaseUserResponse> users = chatGroupUsers.stream()
                            .map(c -> modelMapper.map(c.getUser(), BaseUserResponse.class))
                            .collect(Collectors.toSet());
                    chatGroupResponse.setUsers(users);
                    //
                    return chatGroupResponse;
                })
                .collect(Collectors.toList());
        return BaseResponse.success(chatGroupResponses, "get chat groups success!");
    }

    public ResponseEntity<?> createChatGroup(ChatGroupRequest request) {
        Set<Long> memberIds = request.getMemberIds();
        if (memberIds == null || memberIds.isEmpty()) {
            return BaseResponse.badRequest("create group failed: member users is empty");
        }
        User currentUser = userService.getCurrentUser();
        ChatGroup chatGroup = null;
        //check exist group between two users
        if (memberIds.size() == 1) {
            List<Long> list = memberIds.stream().toList();
            chatGroup = service.getGroupByTwoUsers(list.get(0), currentUser.getId());
            //return bad request if group between two users exists
            if (chatGroup != null) {
                return BaseResponse.badRequest("Group between two user exist");
            }
        }
        //create new chat group not exist
        chatGroup = new ChatGroup();
        chatGroup.setStatus(1);
        chatGroup.setName(request.getName());
        chatGroup = service.saveFlush(chatGroup);
        ChatGroupUser chatGroupUser = new ChatGroupUser();
        chatGroupUser.setUser(currentUser);
        chatGroupUser.setChatGroup(chatGroup);
        chatGroupUser.setStatus(1);
        chatGroupUser= chatGroupUserService.saveFlush(chatGroupUser);
        chatGroup.addChatGroupUser(chatGroupUser);
        for (Long id :
                memberIds) {
            User user = userService.getById(id, 1);
            if (user != null) {
                ChatGroupUser groupUser = new ChatGroupUser();
                groupUser.setUser(user);
                groupUser.setStatus(1);
                chatGroup.getUsers().add(groupUser);
                groupUser.setChatGroup(chatGroup);
                chatGroupUser.setChatGroup(chatGroup);
                groupUser = chatGroupUserService.saveFlush(groupUser);
                chatGroup.addChatGroupUser(groupUser);
            }
        }
        chatGroup = service.saveFlush(chatGroup);
        return BaseResponse.success("create chat group success");


    }
}
