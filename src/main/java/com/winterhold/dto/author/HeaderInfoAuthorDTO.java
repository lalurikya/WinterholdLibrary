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
public class HeaderInfoAuthorDTO {
    private String name;
    private LocalDate birthDate;
    private LocalDate deceasedDate;
    private String Education;
    private String summary;
}
