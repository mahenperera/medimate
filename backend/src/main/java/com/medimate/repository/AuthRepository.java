package com.medimate.repository;

import com.medimate.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<AppUser, UUID> {

    AppUser findByEmail(String email);

    boolean existsByEmail(String email);
}
