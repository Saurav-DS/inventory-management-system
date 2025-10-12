package com.saurav.ims.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saurav.ims.dto.SupplierDTO;
import com.saurav.ims.dto.SupplierRequestDTO;
import com.saurav.ims.exception.NotFoundException;
import com.saurav.ims.model.Supplier;
import com.saurav.ims.repository.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Page<SupplierDTO> getAllSuppliers(Pageable pageable) {
        return supplierRepository.findAllSuppliers(pageable);
    }

    public SupplierDTO createSupplier(SupplierRequestDTO request) {
    	
        Supplier supplier = new Supplier();
        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPhone(request.getPhone());
        
        
        Supplier saved = supplierRepository.save(supplier);
        
        SupplierDTO supplierDto = new SupplierDTO(saved.getId(),saved.getName(),saved.getEmail(),saved.getPhone());
        
        return supplierDto;
    }

    public SupplierDTO updateSupplier(Long id, SupplierRequestDTO request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found with id: " + id));
        
        supplier.setName(request.getName());
        supplier.setEmail(request.getEmail());
        supplier.setPhone(request.getPhone());
        
        Supplier updated = supplierRepository.save(supplier);
        
        SupplierDTO supplierDto = new SupplierDTO(updated.getId(),updated.getName(),updated.getEmail(),updated.getPhone());
        
        return supplierDto;
    }

    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new NotFoundException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }
}
