package com.winterhold.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name="Author")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id//untuk menentukan Primary Key menggunakan JPA
    @GeneratedValue(strategy= GenerationType.IDENTITY)//untuk auto-increment
    @Column(name="Id")
    private Long id;

    @Column(name="Title")
    private String title;

    @Column(name="FirstName")
    private String firstName;

    @Column(name="LastName")
    private String lastName;

    @Column(name="BirthDate")
    private LocalDate birthDate;

    @Column(name="DeceasedDate")
    private LocalDate deceasedDate;

    @Column(name="Education")
    private String education;

    @Column(name="Summary")
    private String summary;
}
