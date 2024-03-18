package com.winterhold.dao;

import com.winterhold.dto.loan.LoanRowDTO;
import com.winterhold.entity.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("""
        SELECT new com.winterhold.dto.loan.LoanRowDTO(loa.id,
        boo.title,
        CONCAT(cus.firstName, ' ', cus.lastName), 
        loa.loanDate,
        loa.dueDate,
        loa.returnDate)
        FROM Loan AS loa
            INNER JOIN loa.book AS boo
            INNER JOIN loa.customer AS cus
        WHERE boo.title LIKE %:title% 
            AND CONCAT(cus.firstName, ' ', cus.lastName) LIKE %:customerName%
        """)
    public Page<LoanRowDTO> getRow(@Param("title") String title,
                                   @Param("customerName") String customerName,
                                   Pageable pageable);

    @Query("""
            SELECT COUNT(loa.id)
            FROM Loan AS loa
                JOIN loa.book AS boo
            WHERE boo.code = :code 
                AND loa.loanDate IS NOT NULL
            """)
    public Long countByBook(@Param("code") String code);

    @Query("""
            SELECT COUNT(loa.id)
            FROM Loan AS loa
                JOIN loa.customer AS cus
            WHERE cus.membershipNumber = :membershipNumber 
                AND loa.loanDate IS NOT NULL
            """)
    public Long countByCustomer(@Param("membershipNumber") String membershipNumber);
}
