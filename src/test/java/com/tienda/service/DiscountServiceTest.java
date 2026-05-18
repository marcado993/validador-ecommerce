package com.tienda.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private DiscountService discountService;

    @Test
    void shouldApplyVolumeDiscountWhenQuantityGreaterThan10() {
        double total = 1000.0;
        int quantity = 11;

        double result = discountService.calculateDiscount(total, quantity, 1L);

        assertEquals(150.0, result, 0.0001);
    }

    @Test
    void shouldApplyVipDiscountWhenVipAndTotalGreaterThan500() {
        long customerId = 2L;
        when(customerService.isVipCustomer(customerId)).thenReturn(true);

        double result = discountService.calculateDiscount(600.0, 5, customerId);

        assertEquals(60.0, result, 0.0001);
    }
}
