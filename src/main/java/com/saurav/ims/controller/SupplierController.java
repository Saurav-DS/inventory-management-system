package com.saurav.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saurav.ims.dto.SupplierDTO;
import com.saurav.ims.dto.SupplierRequestDTO;
import com.saurav.ims.service.SupplierService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
	
	@Autowired
    private SupplierService service;

    @GetMapping
    public Page<SupplierDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAllSuppliers(pageable);
    }

    @PostMapping
    public SupplierDTO create(@RequestBody @Valid SupplierRequestDTO supplierRequest) {
        return service.createSupplier(supplierRequest);
    }

    @PutMapping("{id}")
    public SupplierDTO update(@PathVariable Long id, @RequestBody @Valid SupplierRequestDTO supplierRequest) {
        return service.updateSupplier(id, supplierRequest);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.deleteSupplier(id);
    }
}
