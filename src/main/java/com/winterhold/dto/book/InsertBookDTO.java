package com.winterhold.dto.book;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsertBookDTO {
    @Size(max = 20,message = "Tidak boleh lebih dari 20 karakter")
    @NotEmpty(message = "Tidak boleh kosong")
    private String code;

    @Size(max = 100,message = "Tidak boleh lebih dari 100 karakter")
    @NotEmpty(message = "Tidak boleh kosong")
    private String title;

    private String categoryName;

    @NotNull(message = "Harus dipilih")
    private Long authorId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Tidak boleh mengisi dengan waktu masa depan")
    private LocalDate releaseDate;

    @Min(value = 1,message = "Tidak boleh kurang dari 1")
    private Integer totalPage;

    @Size(max = 500,message = "Tidak boleh lebih dari 500 karakter")
    private String summary;
}
