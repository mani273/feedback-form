package com.exterro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exterro.entity.Question;
import com.exterro.entity.UserEntity;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	List<Question> findByBatch(String batch);
}
