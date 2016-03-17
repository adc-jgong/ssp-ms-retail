package com.tenx.ms.retail.stock.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tenx.ms.retail.products.domain.ProductEntity;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.store.domain.StoreEntity;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long>{
	Page<StockEntity> findByStore(StoreEntity store, Pageable pageable);
	Optional<StockEntity> findByStoreAndProduct(StoreEntity store, ProductEntity product);
}
