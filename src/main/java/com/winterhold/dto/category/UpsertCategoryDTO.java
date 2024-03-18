package com.winterhold.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpsertCategoryDTO {
    @NotBlank(message = "Name wajib di-input.")
    @Size(max=100, message = "Tidak boleh lebih dari 100 chars.")
    private String name;

    @NotNull(message = "Floor wajib di-input.")
    private Integer floor;

    @NotBlank(message = "Isle wajib di-input.")
    @Size(max=10, message = "Tidak boleh lebih dari 10 chars.")
    private String isle;

    @NotBlank(message = "Bay wajib di-input.")
    @Size(max=10, message = "Tidak boleh lebih dari 10 chars.")
    private String bay;
}
