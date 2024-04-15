package ru.phonemasters.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.phonemasters.entities.Order;
import ru.phonemasters.repositories.OrderRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrders(Long phoneNumber) {
        String stringNumber = String.valueOf(phoneNumber);
        if (stringNumber.startsWith("7")) {
            stringNumber = stringNumber.replaceFirst("7", "8");
        }
        return orderRepository.findAllByPhoneNumber(Long.parseLong(stringNumber));
    }

    public List<Order> getAllOrders() {
        System.out.println(orderRepository.findAll());
        return orderRepository.findAll();
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

/*    @PostConstruct
    public void addOrder() {
        Order order = new Order(2L, 1L, "Павел", 89967632663L, "Телефон сел", "", BigDecimal.valueOf(Long.valueOf(100)), BigDecimal.valueOf(Long.valueOf(100)), Order.RepairState.PENDING, Order.PaymentState.FULL_PAID);

        saveOrder(order);
    }*/

}
