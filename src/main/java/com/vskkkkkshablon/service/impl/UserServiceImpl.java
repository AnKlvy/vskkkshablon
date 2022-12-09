package com.vskkkkkshablon.service.impl;

import com.vskkkkkshablon.entities.Roles;
import com.vskkkkkshablon.entities.Users;
import com.vskkkkkshablon.repositories.RoleRepository;
import com.vskkkkkshablon.repositories.UserRepository;
import com.vskkkkkshablon.service.UserService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

    Users myUser = userRepository.findByEmail(s);
    if (myUser != null) {
      User secUser = new User(myUser.getEmail(), myUser.getPassword(), myUser.getRoles());
      return secUser;
    }
    throw new UsernameNotFoundException("User Not Found");
  }

  @Override
  public Users getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public Users createUser(Users user) {
    Users checkUser = userRepository.findByEmail(user.getEmail());
    if (checkUser == null) {
      Roles role = roleRepository.findByRole("ROLE_USER");
      if (role != null) {
        ArrayList<Roles> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
      }
    }
    return null;
  }
}
