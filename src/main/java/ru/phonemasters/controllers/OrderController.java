package ru.phonemasters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.phonemasters.entities.Order;
import ru.phonemasters.services.OrderService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/orders")
    public String getOrdersPage(Model model, @PathVariable(required = false) String phoneNumber) {
        List<Order> orders = new ArrayList<>();
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
        System.out.println(orders);
        return "Orders";
    }
}
