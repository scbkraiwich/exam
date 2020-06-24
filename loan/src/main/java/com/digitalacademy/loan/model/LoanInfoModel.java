package com.digitalacademy.loan.model;

import com.digitalacademy.loan.service.LoanService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoanInfoModel {
    private Long id;
    private String status;
    private String accountPayable;
    private String accountReceivable;
    private double principalAmount;
}
