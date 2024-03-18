package com.winterhold.dto.loan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpsertLoanDTO {
    private Long id;

    @NotBlank(message = "Customer Number wajib di-input.")
    @Size(max=20, message = "Tidak boleh lebih dari 20 chars.")
    private String customerNumber;

    @NotBlank(message = "Book Code wajib di-input.")
    @Size(max=20, message = "Tidak boleh lebih dari 20 chars.")
    private String bookCode;

    @NotNull(message = "Loan Date wajib di-input.")
    private LocalDate loanDate;

    private LocalDate dueDate;
    private LocalDate returnDate;
    private String note;
}
