package com.ofis.rezervasyon.repository;

import com.ofis.rezervasyon.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.ofis.rezervasyon.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}