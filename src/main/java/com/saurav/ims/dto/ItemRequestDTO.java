package com.saurav.ims.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDTO {
	@NotBlank(message = "Item name is required")
    private String name;
	
	@NotBlank(message = "Category is required")
    private String category;
    
	@NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than zero")
    private double price;
    
	@NotNull(message = "Stock quantity cannot be null")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private int stockQuantity;
    
	@NotNull(message = "Supplier ID is required")
    private Long supplierId;
}
