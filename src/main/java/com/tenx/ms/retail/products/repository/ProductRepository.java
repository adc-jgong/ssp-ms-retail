package com.tenx.ms.retail.products.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tenx.ms.retail.products.domain.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Serializable>{
	Optional<ProductEntity> findByProductName(String productName);
	Optional<ProductEntity> findByProductId(Long productId);
	Optional<ProductEntity> findBySku(String sku);	
}
