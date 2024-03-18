package com.winterhold.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name="Book")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id//untuk menentukan Primary Key menggunakan JPA
    @Column(name="Code")
    private String code;

    @Column(name="Title")
    private String title;

    @Column(name="CategoryName")
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "CategoryName", insertable = false, updatable = false)
    private Category category;

    @Column(name="AuthorId")
    private Long authorId;

    @ManyToOne
    @JoinColumn(name = "AuthorId", insertable = false, updatable = false)
    private Author author;

    @Column(name="IsBorrowed")
    private Boolean isBorrowed;

    @Column(name="Summary")
    private String summary;

    @Column(name="ReleaseDate")
    private LocalDate releaseDate;

    @Column(name="TotalPage")
    private Integer totalPage;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;
}
