package com.winterhold.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookByCategoryDTO {
    private String code;
    private String title;
    private String authorName;
    private Boolean isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;
    private String summary;
}
