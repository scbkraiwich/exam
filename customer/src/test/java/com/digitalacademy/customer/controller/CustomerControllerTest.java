package com.digitalacademy.customer.controller;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.service.CustomerService;
import com.digitalacademy.customer.support.CustomerSupportTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mvc;


    public static final String customerUrl = "/customer/";
    public static final String customerListUrl = customerUrl + "list/";
    public static final String customerNameUrl = customerUrl + "name/";

    @BeforeEach
    private void setup() {
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService);
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testGetCustomerList() throws Exception {
        when(customerService.getCustomerList()).thenReturn(CustomerSupportTest.getCustomerList());
        MvcResult mvcResult = mvc.perform(get(customerListUrl)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        Customer customer1 = CustomerSupportTest.getCustomerList().get(0);
        assertEquals(customer1.getId().toString(), jsonArray.getJSONObject(0).get("id").toString());
        assertEquals(customer1.getFirstName(), jsonArray.getJSONObject(0).get("firstName").toString());
        assertEquals(customer1.getLastName(), jsonArray.getJSONObject(0).get("lastName").toString());
        assertEquals(customer1.getEmail(), jsonArray.getJSONObject(0).get("email").toString());
        assertEquals(customer1.getPhoneNo(), jsonArray.getJSONObject(0).get("phoneNo").toString());
        assertEquals(customer1.getAge().toString(), jsonArray.getJSONObject(0).get("age").toString());

        Customer customer2 = CustomerSupportTest.getCustomerList().get(1);
        assertEquals(customer2.getId().toString(), jsonArray.getJSONObject(1).get("id").toString());
        assertEquals(customer2.getFirstName(), jsonArray.getJSONObject(1).get("firstName").toString());
        assertEquals(customer2.getLastName(), jsonArray.getJSONObject(1).get("lastName").toString());
        assertEquals(customer2.getEmail(), jsonArray.getJSONObject(1).get("email").toString());
        assertEquals(customer2.getPhoneNo(), jsonArray.getJSONObject(1).get("phoneNo").toString());
        assertEquals(customer2.getAge().toString(), jsonArray.getJSONObject(1).get("age").toString());
    }

    @Test
    void testGetCustomerById() throws Exception {
        Long reqId = 1L;
        when(customerService.getCustomerById(reqId)).thenReturn(CustomerSupportTest.getCustomer());
        MvcResult mvcResult = mvc.perform(get(customerUrl + reqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals(CustomerSupportTest.getCustomer().getId().toString(), resObject.get("id").toString());
        assertEquals(CustomerSupportTest.getCustomer().getFirstName(), resObject.get("firstName").toString());
        assertEquals(CustomerSupportTest.getCustomer().getLastName(), resObject.get("lastName").toString());
        assertEquals(CustomerSupportTest.getCustomer().getEmail(), resObject.get("email").toString());
        assertEquals(CustomerSupportTest.getCustomer().getPhoneNo(), resObject.get("phoneNo").toString());
        assertEquals(CustomerSupportTest.getCustomer().getAge().toString(), resObject.get("age").toString());
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        Long reqId = 2L;

        MvcResult mvcResult = mvc.perform(get(customerUrl + reqId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void testGetCustomerByName() throws Exception {
        String reqName = "bruh";
        when(customerService.getCustomerByName(reqName)).thenReturn(CustomerSupportTest.getDuplicateCustomerList());
        MvcResult mvcResult = mvc.perform(get(customerNameUrl + "?name=" + reqName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        Customer customer1 = CustomerSupportTest.getDuplicateCustomerList().get(0);
        assertEquals(customer1.getId().toString(), jsonArray.getJSONObject(0).get("id").toString());
        assertEquals(customer1.getFirstName(), jsonArray.getJSONObject(0).get("firstName").toString());
        assertEquals(customer1.getLastName(), jsonArray.getJSONObject(0).get("lastName").toString());
        assertEquals(customer1.getEmail(), jsonArray.getJSONObject(0).get("email").toString());
        assertEquals(customer1.getPhoneNo(), jsonArray.getJSONObject(0).get("phoneNo").toString());
        assertEquals(customer1.getAge().toString(), jsonArray.getJSONObject(0).get("age").toString());

        Customer customer2 = CustomerSupportTest.getDuplicateCustomerList().get(1);
        assertEquals(customer2.getId().toString(), jsonArray.getJSONObject(1).get("id").toString());
        assertEquals(customer2.getFirstName(), jsonArray.getJSONObject(1).get("firstName").toString());
        assertEquals(customer2.getLastName(), jsonArray.getJSONObject(1).get("lastName").toString());
        assertEquals(customer2.getEmail(), jsonArray.getJSONObject(1).get("email").toString());
        assertEquals(customer2.getPhoneNo(), jsonArray.getJSONObject(1).get("phoneNo").toString());
        assertEquals(customer2.getAge().toString(), jsonArray.getJSONObject(1).get("age").toString());
    }

    @Test
    void testGetCustomerByNameNotFound() throws Exception {
        String reqName = "jeff";

        MvcResult mvcResult = mvc.perform(get(customerNameUrl + "?name=" + reqName))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void testCreateCustomer() throws Exception {
        Customer customer = CustomerSupportTest.getCustomer();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String reqJson = writer.writeValueAsString(customer);
        when(customerService.createCustomer(customer)).thenReturn(CustomerSupportTest.getCustomer());

        MvcResult mvcResult = mvc.perform(post(customerUrl).contentType(MediaType.APPLICATION_JSON).content(reqJson))
                .andExpect(status().isCreated())
                .andReturn();

        JSONObject resObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals(CustomerSupportTest.getCustomer().getId().toString(), resObject.get("id").toString());
        assertEquals(CustomerSupportTest.getCustomer().getFirstName(), resObject.get("firstName").toString());
        assertEquals(CustomerSupportTest.getCustomer().getLastName(), resObject.get("lastName").toString());
        assertEquals(CustomerSupportTest.getCustomer().getEmail(), resObject.get("email").toString());
        assertEquals(CustomerSupportTest.getCustomer().getPhoneNo(), resObject.get("phoneNo").toString());
        assertEquals(CustomerSupportTest.getCustomer().getAge().toString(), resObject.get("age").toString());
    }

    @Test
    void testCreateCustomerWithNameEmpty() throws Exception {
        Customer customer = CustomerSupportTest.getCustomer();
        customer.setFirstName("");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String reqJson = writer.writeValueAsString(customer);

        MvcResult mvcResult = mvc.perform(post(customerUrl).contentType(MediaType.APPLICATION_JSON).content(reqJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer oldCustomer = CustomerSupportTest.getCustomerList().get(0);
        Long reqId = oldCustomer.getId();

        Customer newCustomer = CustomerSupportTest.getCustomerList().get(0);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String reqJson = writer.writeValueAsString(newCustomer);

        when(customerService.updateCustomer(reqId, newCustomer)).thenReturn(CustomerSupportTest.getCustomerList().get(0));

        MvcResult mvcResult = mvc.perform(put(customerUrl + "/" + reqId).contentType(MediaType.APPLICATION_JSON).content(reqJson))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject resObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals(newCustomer.getId().toString(), resObject.get("id").toString());
        assertEquals(newCustomer.getFirstName(), resObject.get("firstName").toString());
        assertEquals(newCustomer.getLastName(), resObject.get("lastName").toString());
        assertEquals(newCustomer.getEmail(), resObject.get("email").toString());
        assertEquals(newCustomer.getPhoneNo(), resObject.get("phoneNo").toString());
        assertEquals(newCustomer.getAge().toString(), resObject.get("age").toString());
    }

    @Test
    void testUpdateCustomerNotFound() throws Exception {
        Customer oldCustomer = CustomerSupportTest.getCustomerList().get(0);
        Long reqId = oldCustomer.getId();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
        String reqJson = writer.writeValueAsString(oldCustomer);

        when(customerService.updateCustomer(reqId, oldCustomer)).thenReturn(null);

        MvcResult mvcResult = mvc.perform(put(customerUrl + "/" + reqId).contentType(MediaType.APPLICATION_JSON).content(reqJson))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(customerService, times(1)).updateCustomer(reqId, oldCustomer);
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer oldCustomer = CustomerSupportTest.getCustomerList().get(0);
        Long reqId = oldCustomer.getId();

        when(customerService.deleteCustomer(reqId)).thenReturn(true);

        MvcResult mvcResult = mvc.perform(delete(customerUrl + "/" + reqId))
                .andExpect(status().isOk())
                .andReturn();

        verify(customerService, times(1)).deleteCustomer(reqId);
    }

    @Test
    void testDeleteCustomerNotFound() throws Exception {
        Customer oldCustomer = CustomerSupportTest.getCustomerList().get(0);
        Long reqId = 1L;

        when(customerService.deleteCustomer(reqId)).thenReturn(false);

        MvcResult mvcResult = mvc.perform(delete(customerUrl + "/" + reqId))
                .andExpect(status().isNotFound())
                .andReturn();

        verify(customerService, times(1)).deleteCustomer(reqId);
    }
}

