package epn.edu.ec.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        // Arrange
        double total = 1000.0;
        int quantity = 11;

        // Act
        double result = discountService.calculateDiscount(total, quantity, 1L);

        // Assert
        assertEquals(150.0, result, 0.0001);
    }

    @Test
    void shouldApplyVipDiscountWhenVipAndTotalGreaterThan500() {
        // Arrange
        long customerId = 2L;
        when(customerService.isVipCustomer(customerId)).thenReturn(true);

        // Act
        double result = discountService.calculateDiscount(600.0, 5, customerId);

        // Assert
        assertEquals(60.0, result, 0.0001);
    }

    @Test
    void shouldThrowWhenTotalIsNegative() {
        // Arrange
        double total = -1.0;

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
            () -> discountService.calculateDiscount(total, 1, 1L));
    }

    @Test
    void shouldThrowWhenQuantityIsZeroOrLess() {
        // Arrange
        double total = 100.0;
        int quantity = 0;

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
            () -> discountService.calculateDiscount(total, quantity, 1L));
    }

    @Test
    void shouldThrowWhenCustomerIdIsNull() {
        // Arrange
        double total = 100.0;
        int quantity = 1;

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
            () -> discountService.calculateDiscount(total, quantity, null));
    }
}
