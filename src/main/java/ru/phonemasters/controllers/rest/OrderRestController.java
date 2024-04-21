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
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDto) {


        Optional<Order> optionalOrder = orderService.getOrderById(id);
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        Order order = optionalOrder.get();
        order.setName(orderDto.getName());
        order.setPhoneNumber(orderDto.getPhoneNumber());
        order.setOriginalComplaint(orderDto.getOriginalComplaint());
        order.setRealComplaint(orderDto.getRealComplaint());
        order.setOriginalPrice(orderDto.getOriginalPrice());
        order.setAgreedPrice(orderDto.getAgreedPrice());
        order.setRepairState(orderDto.getRepairState());
        order.setPaymentState(orderDto.getPaymentState());

        return ResponseEntity.ok(orderService.saveOrder(order));
    }

    @PostMapping("/create")
    @ResponseBody
    @CrossOrigin(origins = "localhost:8080")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDto) {
        Order.OrderBuilder builder = Order.builder();
        builder.name(orderDto.getName());
        builder.phoneNumber(orderDto.getPhoneNumber());
        builder.deviceModel(orderDto.getDeviceModel());
        builder.originalComplaint(orderDto.getOriginalComplaint());
        builder.originalPrice(orderDto.getOriginalPrice());
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
