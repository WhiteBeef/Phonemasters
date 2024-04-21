package ru.phonemasters.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.phonemasters.entities.Order;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private String name;
    private Long phoneNumber;
    private String deviceModel;
    private String originalComplaint;
    private String realComplaint;
    private Long originalPrice;
    private Long agreedPrice;
    private Order.RepairState repairState;
    private Order.PaymentState paymentState;
}
