package epn.edu.ec.service;

import org.springframework.stereotype.Service;

@Service
public class DiscountService {
    private final CustomerService customerService;

    public DiscountService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public double calculateDiscount(double total, int quantity, Long customerId) {
        if (total < 0 || quantity <= 0 || customerId == null) {
            throw new IllegalArgumentException("Valores de entrada inválidos");
        }

        double discount = 0;

        if (quantity > 10) {
            discount = 0.15;
        } else if (total > 500 && customerService.isVipCustomer(customerId)) {
            discount = 0.10;
        }

        return total * discount;
    }
}
