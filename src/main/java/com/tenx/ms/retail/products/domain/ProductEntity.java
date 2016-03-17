package com.tenx.ms.retail.products.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tenx.ms.retail.stock.domain.StockEntity;

@Entity
@Table(name="product", uniqueConstraints=@UniqueConstraint(columnNames={"sku"}))
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="product_id")
	private Long productId;
	
	@NotNull
	@Column(name="product_name")
	@Size(max=150)
	private String productName;
	
	@NotNull
	@Column(name="product_desc")
	@Size(max=300)
	private String productDesc;
	
	@Column(name="sku")
	@NotNull
	@Size(max=10, min=5)
	private String sku;
	
	@Column(name="price")
	@NotNull	
	private BigDecimal price;
	
	@OneToMany(mappedBy="product")
	private Set<StockEntity> stock = new HashSet<StockEntity>();

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Set<StockEntity> getStock() {
		return stock;
	}

	public void setStock(Set<StockEntity> stock) {
		this.stock = stock;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result
				+ ((productId == null) ? 0 : productId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ProductEntity other = (ProductEntity) obj;
		if (productId == null) {
			if (other.productId != null) {
				return false;
			}
		} else if (!productId.equals(other.productId)) {
			return false;
		}
		return true;
	}

}
