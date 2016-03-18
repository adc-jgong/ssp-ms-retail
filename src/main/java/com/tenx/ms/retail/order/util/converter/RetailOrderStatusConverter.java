package com.tenx.ms.retail.order.util.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.tenx.ms.retail.order.util.enums.RetailOrderStatus;

@Converter(autoApply=true)
public class RetailOrderStatusConverter implements AttributeConverter<RetailOrderStatus, Integer>{
	@Override
	public Integer convertToDatabaseColumn(RetailOrderStatus attribute) {
		switch (attribute) {
		case ORDERED:
			return 1;
		case PACKING:
			return 2;
		case SHIPPED:
			return 3;		
		default:
			throw new IllegalArgumentException("Unknown value: " + attribute);
		}
	}
	
	@Override
	public RetailOrderStatus convertToEntityAttribute(Integer dbData) {
		switch (dbData) {
		case 1:
			return RetailOrderStatus.ORDERED;
		case 2:
			return RetailOrderStatus.PACKING;
		case 3:
			return RetailOrderStatus.SHIPPED;
		default:
			throw new IllegalArgumentException("Unknown value: " + dbData);
		}
	}
}
