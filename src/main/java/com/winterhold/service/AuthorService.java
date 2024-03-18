package com.winterhold.service;

import com.winterhold.dao.AuthorRepository;
import com.winterhold.dao.BookRepository;
import com.winterhold.dto.author.AuthorRowDTO;
import com.winterhold.dto.author.BookByAuthorDTO;
import com.winterhold.dto.author.HeaderInfoAuthorDTO;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.dto.category.BookByCategoryDTO;
import com.winterhold.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public Page<AuthorRowDTO> getRows(String name, Integer page){
        var pageable = PageRequest.of(page - 1, 10, Sort.by("id"));
        var rows = authorRepository.getRow(name, pageable);
        return rows;
    }

    public UpsertAuthorDTO findOne(Long id){
        var entity = authorRepository.findById(id).get();
        var dto = new UpsertAuthorDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate(),
                entity.getDeceasedDate(),
                entity.getEducation(),
                entity.getSummary()
        );
        return dto;
    }

    public void save(UpsertAuthorDTO dto){
        var entity = new Author();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setDeceasedDate(dto.getDeceasedDate());
        entity.setEducation(dto.getEducation());
        entity.setSummary(dto.getSummary());
        authorRepository.save(entity);
    }

    public Long countBookByAuthor(Long id){
        return bookRepository.countByAuthor(id);
    }

    public void delete(Long id){
        authorRepository.deleteById(id);
    }

    public Page<BookByAuthorDTO> getDetails(Long id, Integer page){
        var pageable = PageRequest.of(page - 1, 10, Sort.by("id"));
        return bookRepository.getRowByAuthor(id, pageable);
    }

    public HeaderInfoAuthorDTO getHeaderInfo(Long id){
        var entity = authorRepository.findById(id).get();
        var dto = new HeaderInfoAuthorDTO(
                entity.getTitle() + " " + entity.getFirstName() + " " + entity.getLastName(),
                entity.getBirthDate(),
                entity.getDeceasedDate(),
                entity.getEducation(),
                entity.getSummary()
        );
        return dto;
    }


}
