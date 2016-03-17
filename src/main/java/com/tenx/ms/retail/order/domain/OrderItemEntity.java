package com.tenx.ms.retail.order.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tenx.ms.retail.stock.domain.StockEntity;

@Entity
@Table(name="order_item")
public class OrderItemEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="order_item_id")
	private Long orderItemId;
	
	@Column(name="item_cnt")
	private Integer itemCnt;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="stock_id", nullable=false, updatable=false)
	private StockEntity stock;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="order_id", nullable=false, updatable=false)
	private CustomerOrderEntity order;
	
	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Integer getItemCnt() {
		return itemCnt;
	}

	public void setItemCnt(Integer itemCnt) {
		this.itemCnt = itemCnt;
	}

	public CustomerOrderEntity getOrder() {
		return order;
	}

	public void setOrder(CustomerOrderEntity order) {
		this.order = order;
	}

	public StockEntity getStock() {
		return stock;
	}

	public void setStock(StockEntity stock) {
		this.stock = stock;
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result
				+ ((orderItemId == null) ? 0 : orderItemId.hashCode());
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
		OrderItemEntity other = (OrderItemEntity) obj;
		if (orderItemId == null) {
			if (other.orderItemId != null) {
				return false;
			}
		} else if (!orderItemId.equals(other.orderItemId)) {
			return false;
		}
		return true;
	}
}
