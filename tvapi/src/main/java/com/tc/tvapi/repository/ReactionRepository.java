package com.tc.tvapi.repository;

import com.tc.tvapi.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository  extends JpaRepository<Reaction,Long> {
}
