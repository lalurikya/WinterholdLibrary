package com.winterhold.service;

import com.winterhold.dao.BookRepository;
import com.winterhold.dao.CustomerRepository;
import com.winterhold.dao.LoanRepository;
import com.winterhold.dto.author.UpsertAuthorDTO;
import com.winterhold.dto.customer.CustomerRowDTO;
import com.winterhold.dto.loan.LoanRowDTO;
import com.winterhold.dto.loan.UpsertLoanDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Author;
import com.winterhold.entity.Book;
import com.winterhold.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Page<LoanRowDTO> getRows(String title, String customerName, Integer page) {
        var pageable = PageRequest.of(page - 1, 10, Sort.by("id"));
        var rows = loanRepository.getRow(title, customerName, pageable);
        return rows;
    }

    public UpsertLoanDTO findOne(Long id){
        var entity = loanRepository.findById(id).get();
        var dto = new UpsertLoanDTO(
                entity.getId(),
                entity.getCustomerNumber(),
                entity.getBookCode(),
                entity.getLoanDate(),
                entity.getDueDate(),
                entity.getReturnDate(),
                entity.getNote()
        );
        return dto;
    }

    public void insert(UpsertLoanDTO dto){
        var getBookCode = bookRepository.findById(dto.getBookCode()).get();
        getBookCode.setIsBorrowed(true);
        bookRepository.save(getBookCode);
        var entity = new Loan();
        entity.setId(dto.getId());
        entity.setCustomerNumber(dto.getCustomerNumber());
        entity.setBookCode(dto.getBookCode());
        entity.setLoanDate(dto.getLoanDate());
        entity.setDueDate(dto.getLoanDate().plusDays(5));
        entity.setReturnDate(dto.getReturnDate());
        entity.setNote(dto.getNote());
        loanRepository.save(entity);
    }

    public void update(UpsertLoanDTO dto){
        var entity = new Loan();
        entity.setId(dto.getId());
        entity.setCustomerNumber(dto.getCustomerNumber());
        entity.setBookCode(dto.getBookCode());
        entity.setLoanDate(dto.getLoanDate());
        entity.setDueDate(dto.getDueDate());
        entity.setReturnDate(dto.getReturnDate());
        entity.setNote(dto.getNote());
        loanRepository.save(entity);
    }

    public void delete(Long id){
        loanRepository.deleteById(id);
    }

    public void returnBook(Long id){
        var entity = loanRepository.findById(id).get();
        var getStatusBook = bookRepository.findById(entity.getBookCode()).get();
        getStatusBook.setIsBorrowed(false);
//        bookRepository.save(getStatusBook);
        entity.setReturnDate(LocalDate.now());
        loanRepository.save(entity);
    }

    public List<DropdownDTO> getBookDropdown(){
        return bookRepository.bookDropdown();
    }

    public List<DropdownDTO> getCustomerDropdown(){
        return customerRepository.customerDropdown(LocalDate.now());
    }

}
