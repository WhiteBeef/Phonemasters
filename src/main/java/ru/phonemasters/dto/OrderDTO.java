package ru.phonemasters.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
