package com.tenx.ms.retail.products.rest.dto;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ProductDTO {
	@ApiModelProperty("product id - system generated id")
	@JsonProperty("product_id")
	private Long id;
	
	@NotBlank
	@Length(max=150)
	@ApiModelProperty("product's name")
	private String productName;
	
	@Length(max=300)
	@ApiModelProperty("product's description")
	private String productDescription;
	
	@NotEmpty	
	@Length(max=10, min=5)
	@ApiModelProperty("product's sku")
	private String sku;
	
	@ApiModelProperty("product's price")
	private BigDecimal price;
	
	@ApiModelProperty("product in which store")
	private Long storeId;
	
	@ApiModelProperty("quantity")
	private Integer quantity;
	
	public Long getId() {
		return id;
	}

	public ProductDTO setId(Long id) {
		this.id = id;
		return this;
	}

	public String getProductName() {
		return productName;
	}

	public ProductDTO setProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public ProductDTO setProductDescription(String productDescription) {
		this.productDescription = productDescription;
		return this;
	}

	public String getSku() {
		return sku;
	}

	public ProductDTO setSku(String sku) {
		this.sku = sku;
		return this;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public ProductDTO setPrice(BigDecimal price) {
		this.price = price;
		return this;
	}

	public Long getStoreId() {
		return storeId;
	}

	public ProductDTO setStoreId(Long storeId) {
		this.storeId = storeId;
		return this;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public ProductDTO setQuantity(Integer quantity) {
		this.quantity = quantity;
		return this;
	}	
}
