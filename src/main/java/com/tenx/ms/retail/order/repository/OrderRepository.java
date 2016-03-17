package com.tenx.ms.retail.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tenx.ms.retail.order.domain.CustomerOrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrderEntity, Long>{
	
}
