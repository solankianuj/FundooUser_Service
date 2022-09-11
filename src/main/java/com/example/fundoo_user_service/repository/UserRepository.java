package com.example.fundoo_user_service.repository;

import com.example.fundoo_user_service.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,Long> {
    Optional<UserModel> findByEmailId(String emailId);
}
