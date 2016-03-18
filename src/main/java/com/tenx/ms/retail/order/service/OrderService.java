package com.tenx.ms.retail.order.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenx.ms.retail.order.domain.CustomerOrderEntity;
import com.tenx.ms.retail.order.domain.OrderItemEntity;
import com.tenx.ms.retail.order.repository.OrderItemRepository;
import com.tenx.ms.retail.order.repository.OrderRepository;
import com.tenx.ms.retail.order.rest.dto.OrderDTO;
import com.tenx.ms.retail.order.rest.dto.OrderProductDTO;
import com.tenx.ms.retail.order.util.enums.RetailOrderStatus;
import com.tenx.ms.retail.products.domain.ProductEntity;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.store.domain.StoreEntity;

@Service
public class OrderService {
	@Autowired
	private OrderRepository retailOrderRepository;
	
	@Autowired
	private OrderItemRepository retailOrderItemRepository;
	
	@Autowired
	private StockRepository retailStockRepository;
	
	@Transactional
	public Long createOrder(OrderDTO dto) {
		StoreEntity store = new StoreEntity();
		store.setStoreId(dto.getStoreId());
		final CustomerOrderEntity entity = retailOrderRepository.save(convertTo(dto));
		
		List<OrderItemEntity> itemList = dto.getProducts().stream()
				.map(item -> covertToOrderItem(item, dto.getStoreId(), entity)).collect(Collectors.toList());
		retailOrderItemRepository.save(itemList);
		
		List<StockEntity> stockList = itemList.stream().map(stock -> stock.getStock()).collect(Collectors.toList());
		retailStockRepository.save(stockList);
		return entity.getId();
	}
	
	private OrderItemEntity covertToOrderItem(OrderProductDTO productDto, Long storeId, CustomerOrderEntity order) {
		OrderItemEntity item = new OrderItemEntity();
		item.setOrder(order);
		item.setItemCnt(productDto.getQuantity());
		StoreEntity store = new StoreEntity();
		store.setStoreId(storeId);
		ProductEntity product = new ProductEntity();
		product.setProductId(productDto.getProductId());
		StockEntity stock = retailStockRepository.findByStoreAndProduct(store, product).get();
		Integer stockNumber = stock.getProductCnt();		
		stock.setProductCnt(stockNumber-productDto.getQuantity());
		item.setStock(stock);
		return item;
	}
	
	private CustomerOrderEntity convertTo(OrderDTO dto) {
		CustomerOrderEntity order = new CustomerOrderEntity();		
		order.setEmail(dto.getEmail());
		order.setFirstName(dto.getFirstName());
		order.setLastName(dto.getLastName());
		order.setPhone(dto.getPhone());
		if (dto.getStatus() != null) {
			order.setStatus(RetailOrderStatus.valueOf(dto.getStatus()));
		}
		order.setOrderDate(new Date());		
		return order;
	}
}
