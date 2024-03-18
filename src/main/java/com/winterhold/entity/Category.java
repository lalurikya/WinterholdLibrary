package com.winterhold.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Table(name="Category")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id//untuk menentukan Primary Key menggunakan JPA
    @Column(name="Name")
    private String name;

    @Column(name="Floor")
    private Integer floor;

    @Column(name="Isle")
    private String isle;

    @Column(name="Bay")
    private String bay;

    @OneToMany(mappedBy = "category")
    private List<Book> books;
}
