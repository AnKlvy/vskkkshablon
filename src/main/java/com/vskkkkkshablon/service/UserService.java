package com.vskkkkkshablon.service;

import com.vskkkkkshablon.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  Users getUserByEmail(String email);

}
