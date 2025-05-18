package com.chitfund.user.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.chitfund.user.dto.UserRequest;
import com.chitfund.user.model.User;

public interface UserService extends UserDetailsService {

    User saveUser(UserRequest request);

    User getUserById(Long id);

    List<User> getAllUser();

    User saveUser(User user);

    User deleteUser(Long id);

}
