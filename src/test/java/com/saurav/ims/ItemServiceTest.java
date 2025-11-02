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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.saurav.ims.dto.ItemDTO;
import com.saurav.ims.dto.ItemRequestDTO;
import com.saurav.ims.exception.NotFoundException;
import com.saurav.ims.model.Item;
import com.saurav.ims.model.Supplier;
import com.saurav.ims.repository.ItemRepository;
import com.saurav.ims.repository.SupplierRepository;
import com.saurav.ims.service.ItemService;

@SpringBootTest
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    void testGetAllItems() {
        ItemDTO dto1 = new ItemDTO(1L, "Item1", "Category1", 25000, 10, "Vista");
        ItemDTO dto2 = new ItemDTO(2L, "Item2", "Category2", 30000, 5, "Amazon");
        Page<ItemDTO> page = new PageImpl<>(List.of(dto1, dto2));

        when(itemRepository.findAllItems(PageRequest.of(0, 10))).thenReturn(page);

        Page<ItemDTO> result = itemService.getAllItems(PageRequest.of(0, 10));

        assertEquals(2, result.getContent().size());
        verify(itemRepository, times(1)).findAllItems(PageRequest.of(0, 10));
    }

    @Test
    void testCreateItemSuccess() {
        // Arrange
        ItemRequestDTO request = new ItemRequestDTO();
        request.setName("Item1");
        request.setCategory("CategoryA");
        request.setPrice(100.0);
        request.setStockQuantity(10);
        request.setSupplierId(1L);

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Supplier1");

        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

        Item savedItem = new Item();
        savedItem.setId(100L);
        savedItem.setName(request.getName());
        savedItem.setCategory(request.getCategory());
        savedItem.setPrice(request.getPrice());
        savedItem.setStockQuantity(request.getStockQuantity());
        savedItem.setSupplier(supplier);

        when(itemRepository.save(any(Item.class))).thenReturn(savedItem);

        ItemDTO result = itemService.createItem(request);

        assertEquals(100L, result.getId());
        assertEquals("Item1", result.getName());
        assertEquals("CategoryA", result.getCategory());
        assertEquals("Supplier1", result.getSupplierName());

        verify(itemRepository, times(1)).save(any(Item.class));
        verify(supplierRepository, times(1)).findById(1L);
    }


    @Test
    void testCreateItemSupplierNotFound() {
        ItemRequestDTO request = new ItemRequestDTO();
        request.setSupplierId(99L);

        when(supplierRepository.findById(99L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> itemService.createItem(request));

        assertTrue(exception.getMessage().contains("Supplier not found"));
    }

    @Test
    void testUpdateItemSuccess() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Supplier1");

        Item item = new Item();
        item.setId(1L);
        item.setName("OldName");

        ItemRequestDTO request = new ItemRequestDTO();
        request.setName("NewName");
        request.setCategory("Category1");
        request.setPrice(25000);
        request.setStockQuantity(5);
        request.setSupplierId(1L);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        when(itemRepository.save(any(Item.class))).thenAnswer(i -> i.getArgument(0));

        ItemDTO result = itemService.updateItem(1L, request);

        assertEquals("NewName", result.getName());
        assertEquals("Supplier1", result.getSupplierName());
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void testUpdateItemNotFound() {
        ItemRequestDTO request = new ItemRequestDTO();
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> itemService.updateItem(1L, request));
        assertTrue(ex.getMessage().contains("Item not found"));
    }

    @Test
    void testDeleteItemSuccess() {
        when(itemRepository.existsById(1L)).thenReturn(true);

        itemService.deleteItem(1L);

        verify(itemRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteItemNotFound() {
        when(itemRepository.existsById(1L)).thenReturn(false);

        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> itemService.deleteItem(1L));

        assertTrue(ex.getMessage().contains("Item not found"));
    }
}

