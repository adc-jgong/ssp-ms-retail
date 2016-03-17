package com.tenx.ms.retail.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tenx.ms.retail.order.domain.CustomerOrderEntity;
import com.tenx.ms.retail.order.domain.OrderItemEntity;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long>{
	List<OrderItemEntity> findItemByOrder(CustomerOrderEntity order);
}
