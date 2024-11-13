package com.example.tes.coding.yours.repository;

import com.example.tes.coding.yours.model.Form;
import com.example.tes.coding.yours.model.Response;
import com.example.tes.coding.yours.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FormResponseRepository extends JpaRepository<Response, Long> {

    boolean existsByFormAndUser(Form form, User user);

    @Query("SELECT r FROM Response r JOIN FETCH r.user u JOIN FETCH r.answers a JOIN FETCH a.question q WHERE r.form = :form")
    Page<Response> findByForm(@Param("form") Form form, Pageable pageable);
}

