package com.javaweb.service.impl;

import com.javaweb.DTO.UserDTO;
import com.javaweb.model.RoleEntity;
import com.javaweb.model.UserEntity;
import com.javaweb.repository.RoleRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserEntity register(UserDTO userDTO) throws Exception {
        String email = userDTO.getEmail();
        if(userRepository.existsByEmail(email)){
            throw new DataIntegrityViolationException("Email already exists");
        }
        RoleEntity role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new );
        if(role.getName().toUpperCase().equals("ADMIN")){
            throw new PermissionDeniedDataAccessException("You can't not register an admin accout", );
        }

//        UserEntity newUser = User.buidler...

//        newUser.setRole(role)
        String password = userDTO.getPassword();
        String encodedPassword  = passwordEncoder.encode(password);
        newUser.setPassword(encodedPassword);
        return userRepository.save(newUser);
    }

    @Override
    public String login(String email, String password) throws Exception {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new ...;
        }

        UserEntity existingUser = optionalUser.get();
        if(!passwordEncoder.matches(password, existingUser.getPassword())){
            throw new...
        }
        UserNamePasswordAuthencationToken authencationToken = new UserNamePasswordAuthencationToken(
                email, password,
                existingUser.getAuthorities()
        );

        authencationManager.authencaticate(authencationToken)''
        return jwtTokenUtil.generateToken(existingUser);
    }
}
