package com.digitalacademy.loan.service;

import com.digitalacademy.loan.exception.LoanException;
import com.digitalacademy.loan.model.LoanInfoModel;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanServiceTest {
    @InjectMocks
    LoanService loanService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loanService = new LoanService();
    }

    @DisplayName("Test get loan info by id eqauls 1 should retrurn loan intformation")
    @Test
    void testGetLoanInfoByIdEquals() throws Exception {
        LoanInfoModel resp = loanService.getLoanInfoById(1L);

        assertEquals("1", resp.getId().toString());
        assertEquals("Ok", resp.getStatus());
        assertEquals("121-121-2020", resp.getAccountPayable());
        assertEquals("102-121-2020", resp.getAccountReceivable());
        assertEquals(400000.00, resp.getPrincipalAmount());
    }

    @Test
    void testGetLoanByIdEquals2() throws Exception {
        Long reqParam = 2L;
        LoanException thrown = assertThrows(LoanException.class,
                () -> loanService.getLoanInfoById(reqParam),
                "Expect getLoanInfoById(reqParam) to throw but it didnt'");

        assertEquals(400, thrown.getHttpStatus().value());
        assertEquals("LOAN4002", thrown.getLoanError().getCode());
        assertEquals("Loan information not found.", thrown.getLoanError().getMessage());
    }

    @Test
    void testGetLoanByIdEquals3() throws Exception {
        Long reqParam = 3L;
        Exception thrown = assertThrows(Exception.class,
                () -> loanService.getLoanInfoById(reqParam),
                "Expect getLoanInfoById(reqParam) to throw but it didnt'");


        assertEquals("Test throw new exception", thrown.getMessage());
    }
}
