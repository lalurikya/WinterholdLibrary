package com.winterhold.service;

import com.winterhold.dao.BookRepository;
import com.winterhold.dto.book.BookRowDTO;
import com.winterhold.dto.book.BookSummaryDTO;
import com.winterhold.dto.book.InsertBookDTO;
import com.winterhold.dto.book.UpdateBookDTO;
import com.winterhold.dto.category.BookByCategoryDTO;
import com.winterhold.dto.category.UpsertCategoryDTO;
import com.winterhold.entity.Book;
import com.winterhold.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public BookSummaryDTO getInfo(String code){
        var entity = bookRepository.findById(code).get();
        return new BookSummaryDTO(
                entity.getSummary());
    }

    public Page<BookRowDTO> getRows(String name, String title, String authorName, Integer page){
        var pageable = PageRequest.of(page - 1, 10, Sort.by("id"));
        return bookRepository.getRow(name, title, authorName, pageable);
    }

    public UpdateBookDTO findOne(String code){
        var entity = bookRepository.findById(code).get();
        var dto = new UpdateBookDTO(
                entity.getCode(),
                entity.getTitle(),
                entity.getCategoryName(),
                entity.getAuthorId(),
                entity.getReleaseDate(),
                entity.getTotalPage(),
                entity.getSummary(),
                entity.getIsBorrowed()
        );
        return dto;
    }

    public void insert(InsertBookDTO dto){
        var entity = new Book();
        entity.setCode(dto.getCode());
        entity.setTitle(dto.getTitle());
        entity.setCategoryName(dto.getCategoryName());
        entity.setAuthorId(dto.getAuthorId());
        entity.setReleaseDate(dto.getReleaseDate());
        entity.setTotalPage(dto.getTotalPage());
        entity.setSummary(dto.getSummary());
        entity.setIsBorrowed(false);
        bookRepository.save(entity);
    }

    public void update(UpdateBookDTO dto){
        var entity = new Book();
        entity.setCode(dto.getCode());
        entity.setTitle(dto.getTitle());
        entity.setCategoryName(dto.getCategoryName());
        entity.setAuthorId(dto.getAuthorId());
        entity.setReleaseDate(dto.getReleaseDate());
        entity.setTotalPage(dto.getTotalPage());
        entity.setSummary(dto.getSummary());
        entity.setIsBorrowed(dto.getIsBorrowed());
        bookRepository.save(entity);
    }

    public void delete(String code){
        bookRepository.deleteById(code);
    }
}
