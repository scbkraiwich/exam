package com.digitalacademy.loan.service;

import com.digitalacademy.loan.constants.LoanError;
import com.digitalacademy.loan.exception.LoanException;
import com.digitalacademy.loan.model.LoanInfoModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    private static final Logger logger = LogManager.getLogger(LoanService.class.getName());

    public LoanInfoModel getLoanInfoById(Long id) throws Exception {
        logger.info("get loan info by id {}", id);
        LoanInfoModel loanInfoModel = new LoanInfoModel();
        if(id.equals(1L)) {
            loanInfoModel.setId(1L);
            loanInfoModel.setStatus("Ok");
            loanInfoModel.setAccountPayable("121-121-2020");
            loanInfoModel.setAccountReceivable("102-121-2020");
            loanInfoModel.setPrincipalAmount(400000.00);
        } else if (id.equals(2L)) {
            logger.error("Id {}", id);
            throw new LoanException(
                    LoanError.GET_LOAN_INFO_NOT_FOUND,
                    HttpStatus.BAD_REQUEST
            );
        } else {
            logger.error("Id {}", id);
            throw new Exception("Test throw new exception");
        }
        return loanInfoModel;
    }
}
