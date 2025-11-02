package com.saurav.ims;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.saurav.ims.dto.SupplierDTO;
import com.saurav.ims.dto.SupplierRequestDTO;
import com.saurav.ims.exception.NotFoundException;
import com.saurav.ims.model.Supplier;
import com.saurav.ims.repository.SupplierRepository;
import com.saurav.ims.service.SupplierService;

@SpringBootTest
public class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    @Test
    void testGetAllSuppliers() {
        SupplierDTO s1 = new SupplierDTO(1L, "Supplier1", "s1@example.com", "12345");
        SupplierDTO s2 = new SupplierDTO(2L, "Supplier2", "s2@example.com", "67890");
        Page<SupplierDTO> page = new PageImpl<>(List.of(s1, s2));

        when(supplierRepository.findAllSuppliers(PageRequest.of(0, 10))).thenReturn(page);

        Page<SupplierDTO> result = supplierService.getAllSuppliers(PageRequest.of(0, 10));

        assertEquals(2, result.getContent().size());
        verify(supplierRepository, times(1)).findAllSuppliers(PageRequest.of(0, 10));
    }

    @Test
    void testCreateSupplierSuccess() {
        SupplierRequestDTO request = new SupplierRequestDTO();
        request.setName("Supplier1");
        request.setEmail("s1@example.com");
        request.setPhone("12345");

        when(supplierRepository.save(any(Supplier.class))).thenAnswer(invocation -> {
            Supplier s = invocation.getArgument(0);
            s.setId(1L);
            return s;
        });

        SupplierDTO result = supplierService.createSupplier(request);

        assertEquals("Supplier1", result.getName());
        assertEquals("s1@example.com", result.getEmail());
        assertEquals("12345", result.getPhone());
        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    void testUpdateSupplierSuccess() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("OldName");
        supplier.setEmail("old@example.com");
        supplier.setPhone("0000");

        SupplierRequestDTO request = new SupplierRequestDTO();
        request.setName("NewName");
        request.setEmail("new@example.com");
        request.setPhone("1111");

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(supplierRepository.save(any(Supplier.class))).thenAnswer(i -> i.getArgument(0));

        SupplierDTO result = supplierService.updateSupplier(1L, request);

        assertEquals("NewName", result.getName());
        assertEquals("new@example.com", result.getEmail());
        assertEquals("1111", result.getPhone());
        verify(supplierRepository, times(1)).save(supplier);
    }

    @Test
    void testUpdateSupplierNotFound() {
        SupplierRequestDTO request = new SupplierRequestDTO();
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> supplierService.updateSupplier(1L, request));

        assertTrue(ex.getMessage().contains("Supplier not found"));
    }

    @Test
    void testDeleteSupplierSuccess() {
        when(supplierRepository.existsById(1L)).thenReturn(true);

        supplierService.deleteSupplier(1L);

        verify(supplierRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteSupplierNotFound() {
        when(supplierRepository.existsById(1L)).thenReturn(false);

        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> supplierService.deleteSupplier(1L));

        assertTrue(ex.getMessage().contains("Supplier not found"));
    }
}
