package com.tenx.ms.retail.order.rest.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.tenx.ms.commons.validation.constraints.PhoneNumber;

public class OrderDTO {

	@ApiModelProperty(value="order id, system generated.")
	private Long orderId;
	
	@ApiModelProperty(value="order placed date time")
	private Date orderDate;
	
	@ApiModelProperty(value="order status")
	private String status;
	
	@NotBlank
	@ApiModelProperty(value="order contact's first name")
	private String firstName;
	
	@NotBlank
	@ApiModelProperty(value="order contact's last name")
	private String lastName;
		
	@Email
	@ApiModelProperty(value="order contact's email")
	private String email;
	
	@PhoneNumber
	@ApiModelProperty(value="order contact's phone")
	private String phone;
	
	@ApiModelProperty(value="order belong to the store")
	private Long storeId;
	
	@ApiModelProperty(value="product details")
	@NotEmpty
	@Valid
	private List<OrderProductDTO> products;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public List<OrderProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<OrderProductDTO> products) {
		this.products = products;
	}
	
	
}
