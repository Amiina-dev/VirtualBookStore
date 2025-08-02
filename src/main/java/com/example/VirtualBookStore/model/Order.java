package com.example.VirtualBookStore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private String customerName;
    private String customerEmail;
    private List<Long> bookIds;     // List of book IDs included in the order
    private LocalDate orderDate;
    private boolean fulfilled;
    private Double totalPrice;      // Total price of the order
}