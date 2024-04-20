package ru.phonemasters.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.phonemasters.dto.OrderDTO;
import ru.phonemasters.entities.Order;
import ru.phonemasters.services.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {

    private final OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<Order> updateOrder(@PathVariable Long id,
                                             @RequestParam(required = false) String name,
                                             @RequestParam(required = false) Long phoneNumber,
                                             @RequestParam(required = false) String deviceModel,
                                             @RequestParam(required = false) String originalComplaint,
                                             @RequestParam(required = false) String realComplaint,
                                             @RequestParam(required = false) Long originalPrice,
                                             @RequestParam(required = false) Long agreedPrice,
                                             @RequestParam(required = false) String repairState,
                                             @RequestParam(required = false) String paymentState) {


        Optional<Order> optionalOrder = orderService.getOrderById(id);
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        Order order = optionalOrder.get();
        if (name != null) {
            order.setName(name);
        }
        if (phoneNumber != null) {
            order.setPhoneNumber(phoneNumber);
        }
        if (deviceModel != null) {
            order.setDeviceModel(deviceModel);
        }
        if (originalComplaint != null) {
            order.setOriginalComplaint(originalComplaint);
        }
        if (realComplaint != null) {
            order.setRealComplaint(realComplaint);
        }
        if (originalPrice != null) {
            order.setOriginalPrice(originalPrice);
        }
        if (agreedPrice != null) {
            order.setAgreedPrice(agreedPrice);
        }
        if (repairState != null) {
            order.setRepairState(Order.RepairState.valueOf(repairState.toUpperCase()));
        }
        if (paymentState != null) {
            order.setPaymentState(Order.PaymentState.valueOf(paymentState.toUpperCase()));
        }

        return ResponseEntity.ok(orderService.saveOrder(order));
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Order> createOrder(@RequestParam String name,
                                             @RequestParam Long phoneNumber,
                                             @RequestParam String deviceModel,
                                             @RequestParam String originalComplaint,
                                             @RequestParam Long originalPrice) {
        Order.OrderBuilder builder = Order.builder();
        builder.name(name);
        builder.phoneNumber(phoneNumber);
        builder.deviceModel(deviceModel);
        builder.originalComplaint(originalComplaint);
        builder.originalPrice(originalPrice);
        builder.repairState(Order.RepairState.ACCEPTED);
        builder.paymentState(Order.PaymentState.NOT_PAID);
        return ResponseEntity.ok(orderService.saveOrder(builder.build()));
    }

    @PostMapping("/createmany")
    @ResponseBody
    public ResponseEntity<List<Order>> createOrders(List<OrderDTO> orders) {
        List<Order> orderList = new ArrayList<>();
        for (OrderDTO orderDTO : orders) {
            Order.OrderBuilder builder = Order.builder();
            builder.id((long) orderList.size());
            builder.name(orderDTO.getName());
            builder.phoneNumber(orderDTO.getPhoneNumber());
            builder.deviceModel(orderDTO.getDeviceModel());
            builder.originalComplaint(orderDTO.getOriginalComplaint());
            builder.originalPrice(orderDTO.getOriginalPrice());
            builder.realComplaint(orderDTO.getRealComplaint());
            builder.originalPrice(orderDTO.getAgreedPrice());
            builder.repairState(Order.RepairState.ACCEPTED);
            builder.paymentState(Order.PaymentState.NOT_PAID);
            orderList.add(orderService.saveOrder(builder.build()));
        }
        return ResponseEntity.ok(orderList);
    }
}
