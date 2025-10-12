package com.saurav.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {
    private Long id;
    private String name;
    private String category;
    private double price;
    private int stockQuantity;
    private String supplierName; // Only name of the supplier
}
