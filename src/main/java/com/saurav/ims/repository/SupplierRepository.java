package com.saurav.ims.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saurav.ims.dto.SupplierDTO;
import com.saurav.ims.model.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	
	@Query("SELECT new com.saurav.ims.dto.SupplierDTO(s.id, s.name, s.email, s.phone) FROM Supplier s")
    Page<SupplierDTO> findAllSuppliers(Pageable pageable);
}
