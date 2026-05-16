package com.javaweb.service;

import com.javaweb.DTO.UserDTO;
import com.javaweb.model.UserEntity;

public interface UserService {
     UserEntity register(UserDTO userDTO) throws Exception;

     String login(String email, String password) throws  Exception;
}
