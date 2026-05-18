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
        long customerId = 1L;
        when(customerService.isVipCustomer(customerId)).thenReturn(false);

        // Act
        double result = discountService.calculateDiscount(total, quantity, customerId);

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
    void shouldReturnZeroWhenNotVipAndNoVolumeDiscount() {
        // Arrange
        long customerId = 3L;
        when(customerService.isVipCustomer(customerId)).thenReturn(false);

        // Act
        double result = discountService.calculateDiscount(400.0, 5, customerId);

        // Assert
        assertEquals(0.0, result, 0.0001);
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

    // CASOS BORDE

    /**
     * Valida que no se aplique ningún descuento por volumen cuando la cantidad sea exactamente 10.
     * Esta prueba garantiza que la función calculateDiscount no aplique el descuento del 15 %
     * a los pedidos con una cantidad exacta de 10. Verifica la corrección de la lógica del descuento,
     * ya que el umbral para aplicar el descuento por volumen es estrictamente mayor que 10.
     */
    @Test
    void shouldNotApplyVolumeDiscountWhenQuantityIsExactly10(){
        //Arrange
        double total = 1000.0;
        int quantity = 10;
        long customerId = 1L;
        when(customerService.isVipCustomer(customerId)).thenReturn(false);

        //Act
        double result = discountService.calculateDiscount(total, quantity, customerId);

        //Assert
        assertEquals(0.0, result, 0.001);
    }

    /**
     * Valida que no se aplique el descuento por cliente VIP cuando el total es exactamente 500.0.
     */
    @Test
    void shouldNotApplyVipDiscountWhenTotalIsExactly500() {
        // Arrange
        double total = 500.0; // Límite
        int quantity = 5;
        long customerId = 2L;
        when(customerService.isVipCustomer(customerId)).thenReturn(true);

        // Act
        double result = discountService.calculateDiscount(total, quantity, customerId);

        // Assert
        // El descuento esperado es 0.0 ya que 500.0 no es estrictamente mayor que 500
        assertEquals(0.0, result, 0.0001);
    }

}
