package epn.edu.ec.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import epn.edu.ec.model.customer.CustomerResponse;
import epn.edu.ec.model.customer.CustomersResponse;
import epn.edu.ec.repository.CustomerRepository;
import epn.edu.ec.repository.model.Customer;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class CustomerServiceTest {


    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customerA;
    private Customer customerB;

    @BeforeEach
    public void setUp() {
        customerA = Customer.builder()
                .id(1L)
                .name("John Doe")
                .phone("1234567890")
                .build();
        customerB = Customer.builder()
                .id(2L)
                .name("Jane Doe")
                .phone("0987654321")
                .build();
    }
    

    @Test
    void testCreateCustomer() {

    }

    @Test
    void testDeleteCustomer() {

    }

    @Test
    void testGetCustomerById() {

    }

    @Test
    void getCustomers_ShouldReturnAllCustomersByName() {
        //ARRANGE
        List<Customer> customers = List.of(customerB, customerA);
        when(customerRepository.findAll()).thenReturn(customers);
        //ACT
        CustomersResponse result = customerService.getCustomers();
        //ASSERT
        List<CustomerResponse> expected = List.of(
                new CustomerResponse(customerB.getId(), customerB.getName(), customerB.getPhone()),
                new CustomerResponse(customerA.getId(), customerA.getName(), customerA.getPhone())
        );
        assertEquals(new CustomersResponse(expected), result);
        
    }

    @Test
    void testUpdateCustomer() {

    }
}
