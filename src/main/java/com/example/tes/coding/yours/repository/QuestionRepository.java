package com.example.tes.coding.yours.repository;

import com.example.tes.coding.yours.model.Form;
import com.example.tes.coding.yours.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByIdAndForm(Long id, Form form);
}
