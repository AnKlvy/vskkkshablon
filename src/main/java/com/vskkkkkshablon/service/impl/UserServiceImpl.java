package com.vskkkkkshablon.service.impl;

import com.vskkkkkshablon.entities.Users;
import com.vskkkkkshablon.repositories.UserRepository;
import com.vskkkkkshablon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

    Users myUser = userRepository.findByEmail(s);
    if(myUser!=null){
      User secUser = new User(myUser.getEmail(), myUser.getPassword(), myUser.getRoles());
      return secUser;
    }
   throw new UsernameNotFoundException("User Not Found");
  }

  @Override
  public Users getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
