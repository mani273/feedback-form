package com.exterro.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exterro.entity.FormData;

@Repository
public interface FormDataRepository extends JpaRepository<FormData, Long> {

	List<FormData> findByName(String name);
	
	Optional<FormData> findById(Long Id);


}