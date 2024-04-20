package ru.phonemasters.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.phonemasters.entities.Order;
import ru.phonemasters.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;

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
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }


    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

/*    @PostConstruct
    public void addOrder() {
        Order order = new Order(2L, 1L, "Павел", 89967632663L, "Телефон сел", "", BigDecimal.valueOf(Long.valueOf(100)), BigDecimal.valueOf(Long.valueOf(100)), Order.RepairState.PENDING, Order.PaymentState.FULL_PAID);

        saveOrder(order);
    }*/

}
