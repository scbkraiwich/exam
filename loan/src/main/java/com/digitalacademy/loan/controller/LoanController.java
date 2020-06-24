package com.digitalacademy.loan.controller;

import com.digitalacademy.loan.constants.LoanError;
import com.digitalacademy.loan.constants.Response;
import com.digitalacademy.loan.exception.LoanException;
import com.digitalacademy.loan.model.LoanInfoModel;
import com.digitalacademy.loan.model.ResponseModel;
import com.digitalacademy.loan.model.StatusModel;

import com.digitalacademy.loan.service.LoanService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loan")
public class LoanController {

    public static final Logger logger = LogManager.getLogger(LoanController.class.getName());

    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/info/{id}")
    public HttpEntity<ResponseModel> getLoanInfoById(@PathVariable Long id) {
       try {
           logger.info("get loan info by customer id {}", id);
           LoanInfoModel loanInfoResponse = loanService.getLoanInfoById(id);
           logger.info("Response id {}, Loan info id {}", id, loanInfoResponse);
           StatusModel status = new StatusModel(Response.SUCCESS_CODE.getContent(), Response.SUCCESS.getContent());
           return ResponseEntity.ok(new ResponseModel(status, loanInfoResponse));
       } catch (LoanException e) {
           logger.error("Exception by id {}", id);
           LoanError loanError = e.getLoanError();
           return ResponseEntity.ok(
                   new ResponseModel(
                           new StatusModel(
                                   loanError.getCode(),
                                   loanError.getMessage()
                           )
                   )
           );
       } catch (Exception e) {
           logger.error("Exception by id {}", id);
           LoanError loanError = LoanError.GET_LOAN_INFO_EXCEPTION;
           return new ResponseModel(
                   new StatusModel(
                           loanError.getCode(),
                           loanError.getMessage()
                   )
           ).build(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
}
