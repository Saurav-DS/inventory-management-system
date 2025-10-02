package com.saurav.ims.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saurav.ims.dto.ItemDTO;
import com.saurav.ims.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	@Query("SELECT new com.saurav.ims.dto.ItemDTO(i.id, i.name, i.category, i.price, i.stockQuantity, i.supplier.name) FROM Item i")
	Page<ItemDTO> findAllItems(Pageable pageable);
}
