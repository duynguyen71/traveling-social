package com.tc.tvapi.repository;

import com.tc.tvapi.model.Role;
import com.tc.tvapi.enumm.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole eRole);
}
