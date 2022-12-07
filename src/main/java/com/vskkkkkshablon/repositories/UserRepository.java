package com.vskkkkkshablon.repositories;

import com.vskkkkkshablon.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

  Users findByEmail(String email);

}
