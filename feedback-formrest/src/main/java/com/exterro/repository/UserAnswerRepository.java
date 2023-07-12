package com.exterro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.exterro.entity.UserAnswer;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
	
	//List<UserAnswer> findByBatch(String batch);
	
	@Query("select ua from UserAnswer ua where ua.batch = ?1")
	List<UserAnswer> getFeedbackBatchwise(String batch);

	ResponseEntity<List<UserAnswer>> findUserAnswersByBatch(String batch);
}
