package com.saurav.ims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.saurav.ims.dto.ItemDTO;
import com.saurav.ims.dto.ItemRequestDTO;
import com.saurav.ims.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService service;

    @GetMapping
    public Page<ItemDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAllItems(pageable);
    }

    @PostMapping
    public ItemDTO create(@RequestBody ItemRequestDTO itemRequest) {
        return service.createItem(itemRequest);
    }

    @PutMapping("{id}")
    public ItemDTO update(@PathVariable Long id, @RequestBody ItemRequestDTO itemRequest) {
        return service.updateItem(id, itemRequest);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        service.deleteItem(id);
    }
}
