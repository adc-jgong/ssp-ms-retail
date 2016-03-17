package com.tenx.ms.retail.store.rest.dto;

import io.swagger.annotations.ApiModelProperty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class StoreDTO {
	
	@ApiModelProperty("The Store Id - system generated read Only")	
	private Long storeId;
		
	@ApiModelProperty("The Store Name - unique case sensitive, max 50 characters")	
	@Length(max=50)
	@NotBlank
	private String storeName;
	
	public Long getStoreId() {
		return storeId;
	}
	public StoreDTO setStoreId(Long storeId) {
		this.storeId = storeId;
		return this;
	}
	public String getStoreName() {
		return storeName;
	}
	public StoreDTO setStoreName(String storeName) {
		this.storeName = storeName;
		return this;
	}
}
