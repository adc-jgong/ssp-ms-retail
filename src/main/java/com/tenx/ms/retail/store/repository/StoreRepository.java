package com.tenx.ms.retail.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tenx.ms.retail.store.domain.StoreEntity;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {
	Optional<StoreEntity> findStoresByStoreId(long storeId);
	Optional<StoreEntity> findStoresByStoreName(String storeName);
}
