package ru.phonemasters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.phonemasters.entities.Order;
import ru.phonemasters.services.OrderService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public String getOrdersPage(Model model, @PathVariable(required = false) String phoneNumber) {
        List<Order> orders;
        if (phoneNumber != null) {
            Long number = null;
            try {
                number = Long.parseLong(phoneNumber);
            } catch (NumberFormatException ignored) {
            }
            if (number != null) {
                orders = orderService.getOrders(number);
            } else {
                orders = orderService.getAllOrders();
            }
        } else {
            orders = orderService.getAllOrders();
        }
        model.addAttribute("orders", orders);
        return "orders/Orders";
    }

    @GetMapping("/create")
    public String getOrderCreatePage(Model model) {
        return "orders/CreateOrder";
    }

    @GetMapping("/edit/{id}")
    public String getOrderEditPage(Model model, @PathVariable Long id) {
        Optional<Order> optionalOrder = orderService.getOrderById(id);
        if (optionalOrder.isEmpty()) {
            return "orders/OrderNotFound";
        }
        model.addAttribute("order", optionalOrder.get());
        return "orders/EditOrder";
    }

}
