package com.example.tes.coding.yours.payload.response.form;

import com.example.tes.coding.yours.model.Form;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class GetFormsResponse {

    private List<FormDto> forms;
    private Pagination pagination;

    public static GetFormsResponse of(Page<Form> formPage) {
        Page<FormDto> formDtoPage = formPage.map(FormDto::of);
        return builder()
                .forms(formDtoPage.getContent())
                .pagination(Pagination.of(formDtoPage))
                .build();
    }

    @Getter
    @Builder
    private static class FormDto {
        private Long id;
        private String name;
        private String slug;
        private String description;
        private boolean limitOneResponse;
        private Long creatorId;

        private static FormDto of(Form form) {
            return FormDto.builder()
                    .id(form.getId())
                    .name(form.getName())
                    .slug(form.getSlug())
                    .description(form.getDescription())
                    .limitOneResponse(form.isLimitOneResponse())
                    .creatorId(form.getCreator().getId())
                    .build();
        }
    }

    @Getter
    @Builder
    private static class Pagination {
        private int totalPages;
        private long totalElements;
        private int page;
        private boolean hasNext;
        private boolean hasPrevious;
        private int requestSize;
        private int formSize;

        private static Pagination of(Page<FormDto> formDtoPage) {
            return builder()
                    .totalPages(formDtoPage.getTotalPages())
                    .totalElements(formDtoPage.getTotalElements())
                    .page(formDtoPage.getNumber() + 1)
                    .hasNext(formDtoPage.hasNext())
                    .hasPrevious(formDtoPage.hasPrevious())
                    .requestSize(formDtoPage.getSize())
                    .formSize(formDtoPage.getNumberOfElements())
                    .build();
        }
    }
}

