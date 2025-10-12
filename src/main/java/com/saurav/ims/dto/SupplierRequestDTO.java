package com.saurav.ims.dto;

import com.saurav.ims.config.annotations.ValidPhoneNumber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierRequestDTO {
	@NotBlank(message = "Name is required")
    private String name;
	
	@Email(message = "Invalid email")
    private String email;
	
	@ValidPhoneNumber
    private String phone;
}
