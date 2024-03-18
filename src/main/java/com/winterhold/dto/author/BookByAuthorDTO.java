package com.winterhold.dto.author;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookByAuthorDTO {
    private String title;
    private String categoryName;
    private Boolean isBorrowed;
    private LocalDate releaseDate;
    private Integer totalPage;
}
