package com.javaweb.controller;

import com.javaweb.DTO.UserDTO;
import com.javaweb.model.UserEntity;
import com.javaweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Repository
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO, BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getPassword())){
                return ResponseEntity.badRequest().body("Password not match");
            }
            UserEntity user = userService.register(userDTO);
            return ResponseEntity.ok(user);
        }
        catch (Exception ex){
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }
    @PostMapping("/login")
    public ResponseEntity<String> login (RequestBody UserLoginDTO userLoginDTO){
        try{
            String token = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
            return ResponseEntity.ok(token);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }
}
