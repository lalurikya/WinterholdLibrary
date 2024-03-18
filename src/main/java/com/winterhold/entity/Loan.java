package com.winterhold.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name="Loan")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    @Id//untuk menentukan Primary Key menggunakan JPA
    @GeneratedValue(strategy= GenerationType.IDENTITY)//untuk auto-increment
    @Column(name="Id")
    private Long id;

    @Column(name="CustomerNumber")
    private String customerNumber;

    @ManyToOne
    @JoinColumn(name = "CustomerNumber", insertable = false, updatable = false)
    private Customer customer;

    @Column(name="BookCode")
    private String bookCode;

    @ManyToOne
    @JoinColumn(name = "BookCode", insertable = false, updatable = false)
    private Book book;

    @Column(name="LoanDate")
    private LocalDate loanDate;

    @Column(name="DueDate")
    private LocalDate dueDate;

    @Column(name="ReturnDate")
    private LocalDate returnDate;

    @Column(name="Note")
    private String note;
}
