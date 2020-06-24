package com.digitalacademy.customer.service;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.repositories.CustomerRepository;
import com.digitalacademy.customer.service.CustomerService;
import com.digitalacademy.customer.support.CustomerSupportTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void testGetAllCustomer() {

        ArrayList<Customer> customers = CustomerSupportTest.getCustomerList();

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> customerList = customerService.getCustomerList();

        Customer customer1 = customerList.get(0);
        assertEquals(1L, customer1.getId().longValue());
        assertEquals(customers.get(0).getFirstName(), customer1.getFirstName());
        assertEquals(customers.get(0).getLastName(), customer1.getLastName());
        assertEquals(customers.get(0).getEmail(), customer1.getEmail());
        assertEquals(customers.get(0).getPhoneNo(), customer1.getPhoneNo());
        assertEquals(customers.get(0).getAge(), customer1.getAge());

        Customer customer2 = customerList.get(1);
        assertEquals(2L, customer2.getId().longValue());
        assertEquals(customers.get(1).getFirstName(), customer2.getFirstName());
        assertEquals(customers.get(1).getLastName(), customer2.getLastName());
        assertEquals(customers.get(1).getEmail(), customer2.getEmail());
        assertEquals(customers.get(1).getPhoneNo(), customer2.getPhoneNo());
        assertEquals(customers.get(1).getAge(), customer2.getAge());
    }

    @Test
    void getCustomerById() {

       Customer customer = CustomerSupportTest.getCustomer();

        when(customerRepository.findAllById(1L)).thenReturn(customer);

        Customer customerRes = customerService.getCustomerById(1L);

        assertEquals(1L, customerRes.getId().longValue());
        assertEquals(customer.getFirstName(), customerRes.getFirstName());
        assertEquals(customer.getLastName(), customerRes.getLastName());
        assertEquals(customer.getEmail(), customerRes.getEmail());
        assertEquals(customer.getPhoneNo(), customerRes.getPhoneNo());
        assertEquals(customer.getAge(), customerRes.getAge());
    }

    @Test
    void getCustomerByName() {
        ArrayList<Customer> customers = CustomerSupportTest.getDuplicateCustomerList();

        when(customerRepository.findByFirstName("bruh")).thenReturn(customers);

        List<Customer> customerList = customerService.getCustomerByName("bruh");

        Customer customer1 = customerList.get(0);
        assertEquals(1L, customer1.getId().longValue());
        assertEquals(customers.get(0).getFirstName(), customer1.getFirstName());
        assertEquals(customers.get(0).getLastName(), customer1.getLastName());
        assertEquals(customers.get(0).getEmail(), customer1.getEmail());
        assertEquals(customers.get(0).getPhoneNo(), customer1.getPhoneNo());
        assertEquals(customers.get(0).getAge(), customer1.getAge());

        Customer customer2 = customerList.get(1);
        assertEquals(2L, customer2.getId().longValue());
        assertEquals(customers.get(1).getFirstName(), customer2.getFirstName());
        assertEquals(customers.get(1).getLastName(), customer2.getLastName());
        assertEquals(customers.get(1).getEmail(), customer2.getEmail());
        assertEquals(customers.get(1).getPhoneNo(), customer2.getPhoneNo());
        assertEquals(customers.get(1).getAge(), customer2.getAge());
    }

    @Test
    void testCreateCustomer() {

        Customer customer = CustomerSupportTest.getCustomer();

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer customerRes = customerService.createCustomer(customer);

        assertEquals(1L, customerRes.getId().longValue());
        assertEquals(customer.getFirstName(), customerRes.getFirstName());
        assertEquals(customer.getLastName(), customerRes.getLastName());
        assertEquals(customer.getEmail(), customerRes.getEmail());
        assertEquals(customer.getPhoneNo(), customerRes.getPhoneNo());
        assertEquals(customer.getAge(), customerRes.getAge());
    }

    @Test
    void testUpdateCustomer() {

        Customer beforeCustomer = CustomerSupportTest.getCustomerList().get(0);
        Customer afterCustomer = CustomerSupportTest.getCustomerList().get(1);
        afterCustomer.setId(1L);

        when(customerRepository.findAllById(1L)).thenReturn(beforeCustomer);
        when(customerRepository.save(afterCustomer)).thenReturn(afterCustomer);

        Customer customerRes = customerService.updateCustomer(1L, afterCustomer);

        assertEquals(1L, customerRes.getId().longValue());
        assertEquals(afterCustomer.getFirstName(), customerRes.getFirstName());
        assertEquals(afterCustomer.getLastName(), customerRes.getLastName());
        assertEquals(afterCustomer.getEmail(), customerRes.getEmail());
        assertEquals(afterCustomer.getPhoneNo(), customerRes.getPhoneNo());
        assertEquals(afterCustomer.getAge(), customerRes.getAge());
    }

    @Test
    void updateCustomerFail() {
        Customer beforeCustomer = CustomerSupportTest.getCustomer();

        when(customerRepository.findAllById(1L)).thenReturn(beforeCustomer);

        Customer customerRes = customerService.updateCustomer(4L, beforeCustomer);
        assertEquals(null, customerRes);
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerRepository).deleteById(1L);

        boolean deleteRes = customerService.deleteCustomer(1L);
        assertEquals(true, deleteRes);
        assertTrue(deleteRes);
        assertTrue(customerService.deleteCustomer(1L));
    }

    @Test
    void testDeleteCustomerFail() {
        doThrow(EmptyResultDataAccessException.class).when(customerRepository).deleteById(1L);

        boolean deleteRes = customerService.deleteCustomer(1L);
        assertEquals(false, deleteRes);
        assertFalse(deleteRes);
        assertFalse(customerService.deleteCustomer(1L));
    }

}
