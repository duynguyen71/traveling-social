package com.tv.tvapi.service;

import com.tv.tvapi.repository.ReactionRepository;
import com.tv.tvapi.model.Reaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepo;

    public Reaction getById(Long reactionId){
        return reactionRepo.findById(reactionId).orElse(null);
    }
}
