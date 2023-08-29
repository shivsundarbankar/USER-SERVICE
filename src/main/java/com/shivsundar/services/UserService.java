package com.shivsundar.services;

import com.shivsundar.models.db.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
    User getUserByUserId(Long userId);

}
