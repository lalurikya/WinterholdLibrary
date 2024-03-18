package com.winterhold.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UpdateCustomerDTO {
    @NotBlank(message = "Membership Number wajib di-input.")
    @Size(max=20, message = "Tidak boleh lebih dari 20 chars.")
    private String membershipNumber;

    @NotBlank(message = "First Name wajib di-input.")
    @Size(max=50, message = "Tidak boleh lebih dari 50 chars.")
    private String firstName;

    @Size(max=50, message = "Tidak boleh lebih dari 50 chars.")
    private String lastName;

    @NotNull(message = "Birth Date wajib di-input.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank(message = "Gender wajib di-input.")
    @Size(max=10, message = "Tidak boleh lebih dari 10 chars.")
    private String gender;

    @Size(max=20, message = "Tidak boleh lebih dari 20 chars.")
    private String phone;

    @Size(max=500, message = "Tidak boleh lebih dari 500 chars.")
    private String address;

    private LocalDate membershipExpireDate;
}
