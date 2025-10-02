package com.saurav.ims.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saurav.ims.dto.ItemDTO;
import com.saurav.ims.dto.ItemRequestDTO;
import com.saurav.ims.exception.NotFoundException;
import com.saurav.ims.model.Item;
import com.saurav.ims.model.Supplier;
import com.saurav.ims.repository.ItemRepository;
import com.saurav.ims.repository.SupplierRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Get all items (with pagination using Projection Query)
    public Page<ItemDTO> getAllItems(Pageable pageable) {
        return itemRepository.findAllItems(pageable);
    }

    // Create new item
    public ItemDTO createItem(ItemRequestDTO request) {
        Item item = modelMapper.map(request, Item.class);

        // Fetch and set Supplier
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new NotFoundException("Supplier not found with id: " + request.getSupplierId()));
        item.setSupplier(supplier);

        Item saved = itemRepository.save(item);
        return modelMapper.map(saved, ItemDTO.class);
    }

    // Update existing item
    public ItemDTO updateItem(Long id, ItemRequestDTO request) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found with id: " + id));

        modelMapper.map(request, item);

        // Fetch and set Supplier
        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new NotFoundException("Supplier not found with id: " + request.getSupplierId()));
        item.setSupplier(supplier);

        Item updated = itemRepository.save(item);
        return modelMapper.map(updated, ItemDTO.class);
    }

    // Delete item
    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new NotFoundException("Item not found with id: " + id);
        }
        itemRepository.deleteById(id);
    }
}
