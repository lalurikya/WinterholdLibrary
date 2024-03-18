package com.winterhold.service;

import com.winterhold.dao.AuthorRepository;
import com.winterhold.dao.BookRepository;
import com.winterhold.dao.CategoryRepository;
import com.winterhold.dao.LoanRepository;
import com.winterhold.dto.book.InsertBookDTO;
import com.winterhold.dto.book.UpdateBookDTO;
import com.winterhold.dto.category.BookByCategoryDTO;
import com.winterhold.dto.category.CategoryRowDTO;
import com.winterhold.dto.category.UpsertCategoryDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Book;
import com.winterhold.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private LoanRepository loanRepository;

    public Page<CategoryRowDTO> getRows(String name, Integer page){
        var pageable = PageRequest.of(page - 1, 10, Sort.by("id"));
        var rows = categoryRepository.getRow(name, pageable);
        return rows;
    }

    public UpsertCategoryDTO findOne(String name){
        var entity = categoryRepository.findById(name).get();
        var dto = new UpsertCategoryDTO(
                entity.getName(),
                entity.getFloor(),
                entity.getIsle(),
                entity.getBay()
        );
        return dto;
    }

    public void save(UpsertCategoryDTO dto){
        var entity = new Category();
        entity.setName(dto.getName());
        entity.setFloor(dto.getFloor());
        entity.setIsle(dto.getIsle());
        entity.setBay(dto.getBay());
        categoryRepository.save(entity);
    }

    public Long countBookByCategory(String name){
        return bookRepository.countByCategory(name);
    }

    public void delete(String code){
        categoryRepository.deleteById(code);
    }

    public Long countLoanByBook(String code){
        return loanRepository.countByBook(code);
    }

    public void deleteBook(String code){
        bookRepository.deleteById(code);
    }

    public Page<BookByCategoryDTO> getDetails(String name, String title, String authorName, Integer page){
        var pageable = PageRequest.of(page - 1, 10, Sort.by("id"));
        return bookRepository.getRowByCategory(name, title, authorName, pageable);
    }

    public void insertBook(InsertBookDTO dto) {
        var entity = new Book();
        entity.setCode(dto.getCode());
        entity.setTitle(dto.getTitle());
        entity.setCategoryName(dto.getCategoryName());
        entity.setAuthorId(dto.getAuthorId());
        entity.setReleaseDate(dto.getReleaseDate());
        entity.setTotalPage(dto.getTotalPage());
        entity.setSummary(dto.getSummary());
        entity.setIsBorrowed(false);
        bookRepository.save(entity);}

    public UpdateBookDTO findOneBook(String code){
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

    public void updateBook(UpdateBookDTO dto) {
        var entity = new Book();
        entity.setCode(dto.getCode());
        entity.setTitle(dto.getTitle());
        entity.setCategoryName(dto.getCategoryName());
        entity.setAuthorId(dto.getAuthorId());
        entity.setReleaseDate(dto.getReleaseDate());
        entity.setTotalPage(dto.getTotalPage());
        entity.setSummary(dto.getSummary());
        entity.setIsBorrowed(false);
        bookRepository.save(entity);}

    public List<DropdownDTO> getAuthorDropdown(){
        return bookRepository.getAuthorDropdown();
    }
}
