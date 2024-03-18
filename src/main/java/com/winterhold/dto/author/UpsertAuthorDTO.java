package com.winterhold.dto.author;

import com.winterhold.validation.After;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@After(message = "Birth Date tidak boleh melebihi Decease Date", previousDateField = "birthDate", subsequentDateField = "deceasedDate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpsertAuthorDTO {
    private Long id;

    @NotBlank(message = "Title wajib di-input.")
    @Size(max=10, message = "Tidak boleh lebih dari 10 chars.")
    private String title;

    @NotBlank(message = "First name wajib di-input.")
    @Size(max=50, message = "Tidak boleh lebih dari 50 chars.")
    private String firstName;

    @Size(max=50, message = "Tidak boleh lebih dari 50 chars.")
    private String lastName;

    @NotNull(message = "Birth Date wajib di-input.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Tanggal lahir tidak boleh tanggal sekarang atau masa depan")
    private LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deceasedDate;

    @Size(max=50, message = "Tidak boleh lebih dari 50 chars.")
    private String education;

    @Size(max=500, message = "Tidak boleh lebih dari 500 chars.")
    private String summary;
}
