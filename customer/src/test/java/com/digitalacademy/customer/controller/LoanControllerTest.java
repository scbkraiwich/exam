package com.digitalacademy.customer.controller;

import com.digitalacademy.customer.api.LoanApi;
import com.digitalacademy.customer.api.LoanApiTest;
import com.digitalacademy.customer.model.response.GetLoanInfoResponse;
import com.digitalacademy.customer.support.CustomerSupportTest;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanControllerTest {

    @Mock
    private LoanApi loanApi;

    @InjectMocks
    LoanController loanController;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        loanController = new LoanController(loanApi);
        mvc = MockMvcBuilders.standaloneSetup(loanController).build();

    }

    @Test
    void testGetLoanInfo() throws Exception {
        Long reqId = 1L;
        GetLoanInfoResponse respMock = new GetLoanInfoResponse();
        respMock.setId(1L);
        respMock.setAccountPayable("111-222-333");
        respMock.setAccountReceivable("444-555-666");
        respMock.setPrincipalAmount(999999.0);
        respMock.setStatus("OK");

        when(loanApi.getLoanInfo(reqId)).thenReturn(respMock);
        MvcResult mvcResult = mvc.perform(get("/loan/" + reqId)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resp = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals("1", resp.getString("id"));
        assertEquals("OK", resp.getString("status"));
        assertEquals("111-222-333", resp.getString("account_payable"));
        assertEquals("444-555-666", resp.getString("account_receivable"));
        assertEquals(999999.0, resp.get("principal_amount"));
    }
}
