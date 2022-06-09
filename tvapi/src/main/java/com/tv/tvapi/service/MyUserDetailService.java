package com.tv.tvapi.service;

<<<<<<< HEAD:tvapi/src/main/java/com/tc/tvapi/service/MyUserDetailService.java
<<<<<<< HEAD:tvapi/src/main/java/com/tc/tvapi/service/MyUserDetailService.java
=======
>>>>>>> ef1177e1ac8996f5cc6a92d240cf7f6813d299e4:tvapi/src/main/java/com/tv/tvapi/service/MyUserDetailService.java
import com.tv.tvapi.model.User;
import com.tv.tvapi.repository.UserRepository;
import com.tv.tvapi.model.MyUserDetail;
<<<<<<< HEAD:tvapi/src/main/java/com/tc/tvapi/service/MyUserDetailService.java
=======
import lombok.RequiredArgsConstructor;
>>>>>>> parent of 5a88027 (update multiple modules):tvapi/src/main/java/com/tv/tvapi/service/MyUserDetailService.java
=======
>>>>>>> ef1177e1ac8996f5cc6a92d240cf7f6813d299e4:tvapi/src/main/java/com/tv/tvapi/service/MyUserDetailService.java
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> optional = userRepo.findByUsernameOrEmail(s, s);
        if (optional.isEmpty())
            throw new UsernameNotFoundException("Could not find user");
        User user = optional.get();
        MyUserDetail myUserDetail = new MyUserDetail(user);
        return myUserDetail;
    }
}
