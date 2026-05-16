package com.javaweb.repository;

import com.javaweb.model.BuildingEntity;
import com.javaweb.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
}
