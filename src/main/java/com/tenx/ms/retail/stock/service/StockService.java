package com.tenx.ms.retail.stock.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenx.ms.retail.products.domain.ProductEntity;
import com.tenx.ms.retail.products.repository.ProductRepository;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;

@Service
public class StockService {
	@Autowired
	private StockRepository retailStockRepository;
	
	@Autowired
	private StoreRepository retailStoreRepository;
	
	@Autowired
	private ProductRepository retailProductRepository;
		
	@Transactional
	public Integer createUpdateStock(Long storeId, Long productId, Integer count) {
		StoreEntity store = retailStoreRepository.findOne(storeId);		
		ProductEntity product = retailProductRepository.findOne(productId);
		Optional<StockEntity> stock = retailStockRepository.findByStoreAndProduct(store, product);
		StockEntity stockEntity = null;
		Integer newCount = count == null ? 0 : count;
		if (stock.isPresent()) {
			stockEntity = stock.get();
			newCount = newCount + stockEntity.getProductCnt();
		}else {
			stockEntity = new StockEntity();
			stockEntity.setProduct(product);
			stockEntity.setStore(store);
		}
		stockEntity.setProductCnt(newCount);
		retailStockRepository.save(stock.get());
		return newCount;
	}
}
