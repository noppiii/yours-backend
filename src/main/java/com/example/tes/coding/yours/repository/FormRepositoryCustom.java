package com.example.tes.coding.yours.repository;

import com.example.tes.coding.yours.model.Form;

import java.util.Optional;

public interface FormRepositoryCustom {
    Optional<Form> findForm(String formSlug);
}
