package com.tenx.ms.retail.stock.rest.dto;

import io.swagger.annotations.ApiModelProperty;

public class StockDTO {
	@ApiModelProperty(value="stock_id")
	private Long stockId;
	
	@ApiModelProperty(value="store_id")
	private Long storeId;
	
	@ApiModelProperty(value="product_id")
	private Long productId;
	
	@ApiModelProperty(value="quantity")
	private Integer quantity;

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
