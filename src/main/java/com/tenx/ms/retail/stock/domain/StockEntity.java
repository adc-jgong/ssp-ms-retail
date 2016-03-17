package com.tenx.ms.retail.stock.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tenx.ms.retail.products.domain.ProductEntity;
import com.tenx.ms.retail.store.domain.StoreEntity;

@Entity
@Table(name="stock", uniqueConstraints=@UniqueConstraint(columnNames={"store_id", "product_id"}))
public class StockEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="stock_id")
	private Long stockId;
	
	@Column(name="product_cnt")
	private Integer productCnt;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="store_id", nullable=false, updatable=false)
	private StoreEntity store;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="product_id", nullable=false, updatable=false)
	private ProductEntity product;
	
	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public Integer getProductCnt() {
		return productCnt;
	}

	public void setProductCnt(Integer productCnt) {
		this.productCnt = productCnt;
	}

	public StoreEntity getStore() {
		return store;
	}

	public void setStore(StoreEntity store) {
		this.store = store;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((stockId == null) ? 0 : stockId.hashCode());
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
		StockEntity other = (StockEntity) obj;
		if (stockId == null) {
			if (other.stockId != null) {
				return false;
			}
		} else if (!stockId.equals(other.stockId)) {
			return false;
		}
		return true;
	}
	
}
