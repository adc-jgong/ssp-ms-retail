package com.tenx.ms.retail.order.rest.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class OrderProductDTO {
		
	@NotNull
	@ApiModelProperty (value="product id for product") 
	private Long productId;
	
	
	@NotNull
	@ApiModelProperty (value="quantity")
	private Integer quantity;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productid) {
		this.productId = productid;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
