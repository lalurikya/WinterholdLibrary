package com.winterhold.dao;

import com.winterhold.dto.customer.CustomerRowDTO;
import com.winterhold.dto.utility.DropdownDTO;
import com.winterhold.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("""
        SELECT new com.winterhold.dto.customer.CustomerRowDTO(cus.membershipNumber, CONCAT(cus.firstName, ' ', cus.lastName), cus.membershipExpireDate)
        FROM Customer AS cus
        WHERE cus.membershipNumber LIKE %:membershipNumber%
            AND CONCAT(cus.firstName, ' ', cus.lastName) LIKE %:name%
        """)
    public Page<CustomerRowDTO> getRow(@Param("membershipNumber") String membershipNumber, @Param("name") String name, Pageable pageable);

    @Query("""
            SELECT new com.winterhold.dto.utility.DropdownDTO(
            cus.membershipNumber,
            CONCAT(cus.firstName,' ',cus.lastName))
            FROM Customer AS cus
            WHERE cus.membershipExpireDate > :now
            """)
    public List<DropdownDTO> customerDropdown(@Param("now") LocalDate now);
}
