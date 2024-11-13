package com.example.tes.coding.yours.repository;

import com.example.tes.coding.yours.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FormRepository extends JpaRepository<Form, Long>, FormRepositoryCustom {

    boolean existsBySlug(String slug);

    @Query("SELECT f FROM Form f LEFT JOIN FETCH f.allowedDomains WHERE f.slug = :slug")
    Optional<Form> findFormWithAllowedDomains(@Param("slug") String slug);
}
