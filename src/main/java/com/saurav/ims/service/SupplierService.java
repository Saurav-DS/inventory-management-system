package com.saurav.ims.service;

import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    public Page<SupplierDTO> getAllSuppliers(Pageable pageable) {
        return supplierRepository.findAllSuppliers(pageable);
    }

    public SupplierDTO createSupplier(SupplierRequestDTO request) {
        Supplier supplier = modelMapper.map(request, Supplier.class);
        Supplier saved = supplierRepository.save(supplier);
        return modelMapper.map(saved, SupplierDTO.class);
    }

    public SupplierDTO updateSupplier(Long id, SupplierRequestDTO request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supplier not found with id: " + id));
        modelMapper.map(request, supplier);
        Supplier updated = supplierRepository.save(supplier);
        return modelMapper.map(updated, SupplierDTO.class);
    }

    public void deleteSupplier(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new NotFoundException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }
}
