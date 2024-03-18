package com.winterhold.dao;

import com.winterhold.dto.author.BookByAuthorDTO;
import com.winterhold.dto.book.BookRowDTO;
import com.winterhold.dto.category.BookByCategoryDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    @Query("""
            SELECT new com.winterhold.dto.category.BookByCategoryDTO(
            boo.code,
            boo.title, 
            CONCAT(aut.title, ' ', aut.firstName, ' ', aut.lastName),
            boo.isBorrowed,
            boo.releaseDate,
            boo.totalPage,
            boo.summary)
            FROM Book AS boo
                INNER JOIN boo.author AS aut
                INNER JOIN boo.category AS cat 
            WHERE cat.name LIKE %:name%
                AND boo.title LIKE %:title%
                AND CONCAT(aut.firstName, ' ', aut.lastName) LIKE %:authorName%
            """)
    public Page<BookByCategoryDTO> getRowByCategory(@Param("name") String name,
                                                    @Param("title") String title,
                                                    @Param("authorName") String authorName,
                                                    Pageable pageable);

    @Query("""
            SELECT new com.winterhold.dto.author.BookByAuthorDTO(boo.title,
            cat.name, 
            boo.isBorrowed,
            boo.releaseDate, 
            boo.totalPage)
            FROM Book AS boo
                INNER JOIN boo.category AS cat
                INNER JOIN boo.author AS aut
            WHERE aut.id = :id  
            """)
    public Page<BookByAuthorDTO> getRowByAuthor(@Param("id") Long id, Pageable page);

    @Query("""
            SELECT new com.winterhold.dto.utility.DropdownDTO(
                    aut.id,
                    CONCAT(aut.title,' ',aut.firstName,' ',aut.lastName))
            FROM Author AS aut
            """)
    public List<DropdownDTO> getAuthorDropdown();

    @Query("""
            SELECT new com.winterhold.dto.book.BookRowDTO(
            boo.code,
            boo.title, 
            CONCAT(aut.title, ' ', aut.firstName, ' ', aut.lastName),
            boo.isBorrowed,
            boo.releaseDate,
            boo.totalPage,
            boo.summary)
            FROM Book AS boo
                INNER JOIN boo.author AS aut
                INNER JOIN boo.category AS cat 
            WHERE cat.name LIKE %:name%
                AND boo.title LIKE %:title%
                AND CONCAT(aut.firstName, ' ', aut.lastName) LIKE %:authorName%
            """)
    public Page<BookRowDTO> getRow(@Param("name") String name,
                                             @Param("title") String title,
                                             @Param("authorName") String authorName,
                                             Pageable pageable);

    @Query("""
            SELECT COUNT(boo.code)
            FROM Book AS boo
                JOIN boo.author AS aut
            WHERE aut.id = :id
            """)
    public Long countByAuthor(@Param("id") Long id);

    @Query("""
            SELECT COUNT(boo.code)
            FROM Book AS boo
                JOIN boo.category AS cat
            WHERE cat.name = :name
            """)
    public Long countByCategory(@Param("name") String name);

    @Query("""
            SELECT new com.winterhold.dto.utility.DropdownDTO(
            boo.code,
            boo.title)
            FROM Book AS boo
            WHERE boo.isBorrowed = false
            """)
    public List<DropdownDTO>bookDropdown();
}
