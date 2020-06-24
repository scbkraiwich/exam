package com.digitalacademy.loan.controller;

import com.digitalacademy.loan.constants.LoanError;
import com.digitalacademy.loan.exception.LoanException;
import com.digitalacademy.loan.model.LoanInfoModel;
import com.digitalacademy.loan.service.LoanService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanControllerTest {

    @Mock
    LoanService loanService;

    @InjectMocks
    LoanController loanController;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loanController = new LoanController(loanService);
        mvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @Test
    void testGetLoanInfoByIdEqual1() throws Exception {
        Long reqId = 1L;

        LoanInfoModel loanInfoModel = new LoanInfoModel();
        loanInfoModel.setId(1L);
        loanInfoModel.setStatus("Ok");
        loanInfoModel.setAccountPayable("555-555-2020");
        loanInfoModel.setAccountReceivable("666-666-2020");
        loanInfoModel.setPrincipalAmount(600000.00);

        when(loanService.getLoanInfoById(reqId)).thenReturn(loanInfoModel);

        MvcResult mvcResult = mvc.perform(get("/loan/info/" + reqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resp.getString("status"));
        JSONObject data = new JSONObject(resp.getString("data"));

        assertEquals("0", status.getString("code"));
        assertEquals("success", status.getString("message"));

        assertEquals("1", data.getString("id"));
        assertEquals("Ok", data.getString("status"));
        assertEquals("555-555-2020", data.getString("account_payable"));
        assertEquals("666-666-2020", data.getString("account_receivable"));
        assertEquals(600000, data.get("principal_amount"));
    }

    @Test
    void testGetLoanInfoByIdEqual2() throws Exception {
        Long reqId = 2L;

        when(loanService.getLoanInfoById(reqId)).thenThrow(new LoanException(LoanError.GET_LOAN_INFO_NOT_FOUND, HttpStatus.OK));

        MvcResult mvcResult = mvc.perform(get("/loan/info/" + reqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resp.getString("status"));

        assertEquals("LOAN4002", status.getString("code"));
        assertEquals("Loan information not found.", status.getString("message"));
    }

    @Test
    void testGetLoanInfoByIdEqual3() throws Exception {
        Long reqId = 3L;

        when(loanService.getLoanInfoById(reqId)).thenThrow(new Exception("Test throw new exception"));

        MvcResult mvcResult = mvc.perform(get("/loan/info/" + reqId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resp.getString("status"));

        assertEquals("LOAN4001", status.getString("code"));
        assertEquals("Cannot get loan information.", status.getString("message"));
    }
}
