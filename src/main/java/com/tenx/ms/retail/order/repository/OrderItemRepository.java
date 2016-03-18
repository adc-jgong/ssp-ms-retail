package com.tenx.ms.retail.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tenx.ms.retail.order.domain.CustomerOrderEntity;
import com.tenx.ms.retail.order.domain.OrderItemEntity;
import com.tenx.ms.retail.stock.domain.StockEntity;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long>{
	List<OrderItemEntity> findItemByOrder(CustomerOrderEntity order);
	List<OrderItemEntity> findItemByStock(StockEntity stock);
	
	@Query("SELECT p FROM OrderItemEntity p WHERE p.stock.store.storeId=:storeId")
	List<OrderItemEntity> findItemByStoreId(@Param("storeId") Long storeId);
	
	@Query("SELECT p FROM OrderItemEntity p WHERE p.stock.product.productId=:productId")
	List<OrderItemEntity> findItemByProductId(@Param("productId") Long productId);
}
