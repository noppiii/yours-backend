package com.example.tes.coding.yours.repository.impl;

import com.example.tes.coding.yours.model.Form;
import com.example.tes.coding.yours.model.QForm;
import com.example.tes.coding.yours.model.QUser;
import com.example.tes.coding.yours.repository.FormRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.Optional;

import static com.example.tes.coding.yours.model.QUser.user;

public class FormRepositoryCustomImpl implements FormRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public FormRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public Optional<Form> findForm(String formSlug) {
        QForm form = QForm.form;
        QUser creator = user;

        Form result = jpaQueryFactory
                .selectFrom(form)
                .innerJoin(form.creator, creator)
                .fetchJoin()
                .where(form.slug.eq(formSlug))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
