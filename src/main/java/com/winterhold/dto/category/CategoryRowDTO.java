package com.winterhold.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRowDTO {
    private String name;
    private Integer floor;
    private String isle;
    private String bay;
    private Long totalBooks;
}
