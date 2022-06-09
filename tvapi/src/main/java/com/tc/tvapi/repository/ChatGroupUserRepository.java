package com.tc.tvapi.repository;

import com.tc.tvapi.model.ChatGroup;
import com.tc.tvapi.model.ChatGroupUser;
import com.tc.tvapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatGroupUserRepository extends JpaRepository<ChatGroupUser,Long> {


     List<ChatGroupUser> findByChatGroupAndStatus(ChatGroup chatGroup,Integer status);

     boolean existsByChatGroupAndUserAndStatus(ChatGroup group,User user,Integer status);


}
