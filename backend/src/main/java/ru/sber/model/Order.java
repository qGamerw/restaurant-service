package ru.sber.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Заказ
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Order {
    private Long id;
    private String clientName;
    private String description;
    private String clientPhone;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderCookingTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderCookedTime;
    private String address;
    private String branchAddress;
    private Long branchId;
    private Long employeeRestaurantId;

    @JsonProperty(required = false)
    private List<?> dishesOrders;
}