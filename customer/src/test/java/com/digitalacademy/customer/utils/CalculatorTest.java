package com.digitalacademy.customer.utils;

import lombok.Data;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Data
class Person {
    public String firstName;
    public String lastName;

    Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

public class CalculatorTest {

    private Calculator calculator = new Calculator();
    private Person person = new Person("Bruh", "Moment");


    @Test
    public void testGroupAssertion() {
        assertAll("person",
                () -> assertEquals("Bruh", person.getFirstName()),
                () -> assertEquals("Moment", person.getLastName())
        );
    }

    @Test
    @DisplayName("Test add method for calculator")
    public void testAdd() {
        assertEquals(2, calculator.add(1, 1));
        assertEquals(5, calculator.add(4, 1));
    }

    @Test
    @DisplayName("Test multipy method for calculator")
    public void testMultipy() {
        assertEquals(4, calculator.multiply(2, 2));
        assertEquals(0, calculator.multiply(2, 0));
    }

    @Test
    @DisplayName("Test divide method for calculator")
    public void testDivide() {
        assertEquals(1, calculator.divide(2, 2));
        assertEquals(0.5, calculator.divide(2, 4));
    }
}
