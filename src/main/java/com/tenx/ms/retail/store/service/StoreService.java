package com.tenx.ms.retail.store.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repository.StoreRepository;
import com.tenx.ms.retail.store.rest.dto.StoreDTO;

@Service
public class StoreService {
	@Autowired
	private StoreRepository retailStoreRepository;
	
	public Paginated<StoreDTO> getAllStores(Pageable pageable, String baseLinkPath) throws ValidationException {
	    Page<StoreEntity> curPage = retailStoreRepository.findAll(pageable);
	    List<StoreDTO> storeList = curPage.getContent().stream()
	    		.map(storeEntity -> convertToDTO(storeEntity)).collect(Collectors.toList());
	    return Paginated.wrap(curPage,storeList, baseLinkPath);
	}
	
	public StoreDTO getStoreById(Long storeId) {
		Optional<StoreEntity> store = retailStoreRepository.findStoresByStoreId(storeId);
		return convertToDTO(store.get());
	}
	
	public StoreDTO getStoreByName(String storeName) {
		Optional<StoreEntity> store = retailStoreRepository.findStoresByStoreName(storeName);
		return convertToDTO(store.get());
	}
	
	@Transactional
	public Long createStore(StoreDTO storeDto) {   			
		StoreEntity entity = retailStoreRepository.saveAndFlush(convertToEntity(storeDto));		
		return entity.getStoreId();
    }
    
	private StoreDTO convertToDTO(StoreEntity storeEntity) {
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoreId(storeEntity.getStoreId());
        storeDTO.setStoreName(storeEntity.getStoreName());
        return storeDTO;
    }
	
	private StoreEntity convertToEntity(StoreDTO storeDto) {
        StoreEntity entity = new StoreEntity();
        entity.setStoreName(storeDto.getStoreName());        
        return entity;
    }
}
