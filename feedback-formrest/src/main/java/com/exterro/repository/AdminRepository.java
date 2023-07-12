package com.exterro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exterro.entity.User;

@Repository
	public interface AdminRepository extends JpaRepository<User, Long> {
	    Optional<User> findByUsername(String username);
	}





