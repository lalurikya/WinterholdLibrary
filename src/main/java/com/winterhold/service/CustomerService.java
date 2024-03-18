package com.winterhold.service;

import com.winterhold.dao.CustomerRepository;
import com.winterhold.dao.LoanRepository;
import com.winterhold.dto.customer.CustomerInfoDTO;
import com.winterhold.dto.customer.CustomerRowDTO;
import com.winterhold.dto.customer.InsertCustomerDTO;
import com.winterhold.dto.customer.UpdateCustomerDTO;
import com.winterhold.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanRepository loanRepository;

    public Page<CustomerRowDTO> getRows(String membershipNumber, String name, Integer page) {
        var pageable = PageRequest.of(page - 1, 10, Sort.by("id"));
        var rows = customerRepository.getRow(membershipNumber, name, pageable);
        return rows;
    }

    public void insert(InsertCustomerDTO dto){
        LocalDate membershipExpiredDate = LocalDate.now().plusYears(2);
        if (dto.getMembershipExpireDate() == null) {
            dto.setMembershipExpireDate(membershipExpiredDate);
        }
        var entity = new Customer();
        entity.setMembershipNumber(dto.getMembershipNumber());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setGender(dto.getGender());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        entity.setMembershipExpireDate(membershipExpiredDate);
        customerRepository.save(entity);
    }

    public UpdateCustomerDTO findOne(String membershipNumber){
        var entity = customerRepository.findById(membershipNumber).get();
        var dto = new UpdateCustomerDTO(
                entity.getMembershipNumber(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthDate(),
                entity.getGender(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getMembershipExpireDate()
        );
        return dto;
    }

    public void update(UpdateCustomerDTO dto){
        var entity = new Customer();
        entity.setMembershipNumber(dto.getMembershipNumber());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setBirthDate(dto.getBirthDate());
        entity.setGender(dto.getGender());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        entity.setMembershipExpireDate(dto.getMembershipExpireDate());
        customerRepository.save(entity);
    }

    public Long countLoanByCustomer(String membershipNumber){
        return loanRepository.countByCustomer(membershipNumber);
    }

    public void delete(String membershipNumber){
        customerRepository.deleteById(membershipNumber);
    }

    public CustomerInfoDTO getInfo(String membershipNumber){
        var entity = customerRepository.findById(membershipNumber).get();
        return new CustomerInfoDTO(
                entity.getMembershipNumber(),
                entity.getFirstName() + " " + entity.getLastName(),
                entity.getBirthDate(),
                entity.getGender(),
                entity.getPhone(),
                entity.getAddress());
    }

    public void extendMembershipExpireDate(String membershipNumber){
        var entity = customerRepository.findById(membershipNumber).get();
        entity.setMembershipExpireDate(LocalDate.now().plusYears(2));
        customerRepository.save(entity);
    }
}
