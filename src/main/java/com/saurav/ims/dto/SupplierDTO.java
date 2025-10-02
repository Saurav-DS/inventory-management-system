package com.saurav.ims.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupplierDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
}
