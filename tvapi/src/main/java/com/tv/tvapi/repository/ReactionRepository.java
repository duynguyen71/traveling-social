package com.tv.tvapi.repository;

import com.tv.tvapi.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository  extends JpaRepository<Reaction,Long> {
}
