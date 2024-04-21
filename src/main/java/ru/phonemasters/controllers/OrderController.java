package ru.phonemasters.controllers;

import jakarta.servlet.http.HttpServletResponse;
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

    @GetMapping("/{page}")
    public String getOrdersPage(Model model, @PathVariable(required = false) Long page) {
        List<Order> orders = orderService.getAllOrdersReversed();
        if (page == null) {
            page = 1L;
        }
        long pagesCount = orders.size() / 10 + 1L;

        if (page > pagesCount) {
            return "redirect:/admin/orders/" + pagesCount;
        }

        orders = orders.stream()
                .skip((page - 1) * 10)
                .limit(10)
                .toList();

        model.addAttribute("pagesCount", pagesCount);
        model.addAttribute("orders", orders);
        model.addAttribute("page", page);

        return "orders/Orders";
    }

    @GetMapping("")
    public String redirectToFirstPage() {
        return "redirect:orders/1";
    }

    @GetMapping("/")
    public String secondRedirectToFirstPage() {
        return "redirect:1";
    }

    @GetMapping("/create")
    public String getOrderCreatePage(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
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
