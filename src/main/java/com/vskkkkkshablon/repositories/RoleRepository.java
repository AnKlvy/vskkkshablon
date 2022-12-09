package com.vskkkkkshablon.repositories;

import com.vskkkkkshablon.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Roles, Long> {
Roles findByRole(String role);
}
