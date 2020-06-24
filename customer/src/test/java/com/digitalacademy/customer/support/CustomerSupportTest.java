package com.digitalacademy.customer.support;

import com.digitalacademy.customer.model.Customer;

import java.util.ArrayList;

public class CustomerSupportTest {

    public static ArrayList<Customer> getCustomerList() {
        ArrayList<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jeff");
        customer.setLastName("moment");
        customer.setEmail("jeffmoment@mail.com");
        customer.setPhoneNo("1150");
        customer.setAge(10);
        customers.add(customer);

        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("bruh");
        customer.setLastName("moment");
        customer.setEmail("bruhmoment@mail.com");
        customer.setPhoneNo("1112");
        customer.setAge(30);
        customers.add(customer);
        return customers;
    }

    public static Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("jeff");
        customer.setLastName("moment");
        customer.setEmail("jeffmoment@mail.com");
        customer.setPhoneNo("1150");
        customer.setAge(10);
        return customer;
    }

    public static ArrayList<Customer> getDuplicateCustomerList() {
        ArrayList<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("bruh");
        customer.setLastName("moment");
        customer.setEmail("jeffmoment@mail.com");
        customer.setPhoneNo("1150");
        customer.setAge(10);
        customers.add(customer);

        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("bruh");
        customer.setLastName("moment");
        customer.setEmail("bruhmoment@mail.com");
        customer.setPhoneNo("1112");
        customer.setAge(30);
        customers.add(customer);
        return customers;
    }

}
