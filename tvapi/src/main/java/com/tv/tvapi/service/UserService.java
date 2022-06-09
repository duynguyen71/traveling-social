package com.tv.tvapi.service;

import com.tv.tvapi.model.User;
import com.tv.tvapi.model.MyUserDetail;
import com.tv.tvapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    public User getById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    public User getById(Long id, Integer active) {
        return userRepo.findByIdAndActive(id, active).orElse(null);
    }

    public User getCurrentUser() {
        MyUserDetail myUserDetail = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getById(myUserDetail.getId());
    }

    public boolean existByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    public boolean existByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public boolean existByPhone(String phone) {
        return userRepo.existsByPhone(phone);
    }

    public String generateUniqueCode() {
        String code = String.valueOf(new Random().nextInt(899999) + 100000);
        if (userRepo.findByVerificationCode(code).isPresent()) {
            return generateUniqueCode();
        }
        return code;
    }

    public List<User> search(String username, String fullName, String phone, String email, int active, Pageable pageable) {
        return userRepo.searchUsersNative(username, fullName, phone, email, active, pageable);
    }

    public User getByCode(String code) {
        return userRepo.findByVerificationCode(code).orElse(null);
    }

    public List<User> getTopActiveUsers(Pageable pageable) {
        return userRepo.getTopActiveUsers();
    }
}
