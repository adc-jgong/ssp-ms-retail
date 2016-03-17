package com.tenx.ms.retail.store.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tenx.ms.retail.stock.domain.StockEntity;

@Entity
@Table(name="store")
public class StoreEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="store_id")
	private Long storeId;
	
	@NotNull
	@Column(name="store_name", unique=true)
	@Size(min=1, max=50)
	private String storeName;
	
	@OneToMany(mappedBy="store")
	public Set<StockEntity> stocks = new HashSet<StockEntity>();
	
	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Set<StockEntity> getStocks() {
		return stocks;
	}

	public void setStocks(Set<StockEntity> stocks) {
		this.stocks = stocks;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + ((storeId == null) ? 0 : storeId.hashCode());
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
		StoreEntity other = (StoreEntity) obj;
		if (storeId == null) {
			if (other.storeId != null) {
				return false;
			}
		} else if (!storeId.equals(other.storeId)) {
			return false;
		}
		return true;
	}
}
