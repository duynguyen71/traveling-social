package com.tv.tvapi.service;

import com.tv.tvapi.model.ChatGroup;
import com.tv.tvapi.model.ChatGroupUser;
import com.tv.tvapi.model.User;
import com.tv.tvapi.repository.ChatGroupUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatGroupUserService {

    private final ChatGroupUserRepository repo;

    public ChatGroupUser saveFlush(ChatGroupUser user){
        return repo.saveAndFlush(user);
    }

    public List<ChatGroupUser> getChatUsers(ChatGroup chatGroup,Integer status){
        return repo.findByChatGroupAndStatus(chatGroup, status);
    }

    public boolean existInGroup(ChatGroup chatGroup,User user){
        return repo.existsByChatGroupAndUserAndStatus(chatGroup,user,1);
    }



}
