package com.tenx.ms.retail.products.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.retail.products.domain.ProductEntity;
import com.tenx.ms.retail.products.repository.ProductRepository;
import com.tenx.ms.retail.products.rest.dto.ProductDTO;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;

@Service
public class ProductService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
	@Autowired
	private ProductRepository retailProductRepository;
		
	@Autowired 
	private StockRepository retailStockRepository;
	
	@Autowired 
	private StoreRepository retailStoreRepository;
	
	@Transactional
	public Long createProduct(ProductDTO dto) {
		LOGGER.debug("save product with storeId {} and productName {}", dto.getStoreId(), dto.getProductName());		
		ProductEntity productEntity = retailProductRepository.save(convertToEntity(dto));
		if (dto.getStoreId() != null) {
			Optional<StoreEntity> storeEntity = retailStoreRepository.findStoresByStoreId(dto.getStoreId());
			StockEntity stockEntity = new StockEntity();
			stockEntity.setStore(storeEntity.get());
			stockEntity.setProduct(productEntity);
			int cnt = dto.getQuantity() == null ? null : dto.getQuantity();
			stockEntity.setProductCnt(cnt);
			retailStockRepository.save(stockEntity);
		}
		return productEntity.getProductId();
	}
	
	public Paginated<ProductDTO> getProductList(Long storeId, Pageable pageable, String basePath) {
		StoreEntity store = new StoreEntity();
		store.setStoreId(storeId);
		Page<StockEntity> stockPage = retailStockRepository.findByStore(store, pageable);
		
		List<ProductDTO> productDTOList = stockPage.getContent().stream()
		    		.map(stockEntity -> transform(stockEntity)).collect(Collectors.toList());
		
		return Paginated.wrap(stockPage, productDTOList, basePath);
	}
		
	public ProductDTO getProductById(Long storeId, Long productId) {
		StoreEntity storeEntity = new StoreEntity();
		storeEntity.setStoreId(storeId);
		ProductEntity productEntity = new ProductEntity();
		productEntity.setProductId(productId);
		StockEntity entity = retailStockRepository.findByStoreAndProduct(storeEntity, productEntity).get();		
		ProductDTO dto = convertToDTO(entity.getProduct());		
		dto.setStoreId(storeId);
		return dto;				
	}
	
	private ProductEntity convertToEntity(ProductDTO dto) {
		ProductEntity entity = new ProductEntity();		
		entity.setPrice(dto.getPrice());
		entity.setProductDesc(dto.getProductDescription());
		entity.setProductName(dto.getProductName());
		entity.setSku(dto.getSku());		
		return entity;
	}
	
    public Paginated<ProductDTO> getAllProducts(Pageable pageable, String baseLinkPath) {
        Page<ProductEntity> page = retailProductRepository.findAll(pageable);

        List<ProductDTO> products = page.getContent().stream()
            .map(user -> convertToDTO(user))
            .collect(Collectors.toList());
        return Paginated.wrap(page, products, baseLinkPath);
    }
    
	private ProductDTO convertToDTO(ProductEntity entity) {
		ProductDTO dto = new ProductDTO();
		dto.setId(entity.getProductId());
		dto.setPrice(entity.getPrice());
		dto.setSku(entity.getSku());
		dto.setProductDescription(entity.getProductDesc());
		
		dto.setProductName(entity.getProductName());
		return dto;
	}
	
	private ProductDTO transform(StockEntity entity) {
		ProductDTO dto = convertToDTO(entity.getProduct());			
		dto.setStoreId(entity.getStore().getStoreId());
		return dto;
	}
	
}
