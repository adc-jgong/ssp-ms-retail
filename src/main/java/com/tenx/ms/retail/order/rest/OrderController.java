package com.tenx.ms.retail.order.rest;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.order.rest.dto.OrderDTO;
import com.tenx.ms.retail.order.service.OrderService;

@Api(value="Api for place order")
@RestController
@RequestMapping(value=RestConstants.VERSION_ONE+"/orders")
public class OrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderService retailOrderService;
	
	@ApiOperation(value="place order")
	@ApiResponses(value={@ApiResponse(code=201, message="order placed sucessfully"), 
						 @ApiResponse(code=404, message="stock is not found"), 
						 @ApiResponse(code=412, message="payload is not correct"), 
						 @ApiResponse(code=500, message="internal service error")})
	@RequestMapping(value="/{storeId:\\d+}", method=RequestMethod.POST,consumes={"application/json"})
	public ResponseEntity<ResourceCreated<Long>> createOrder(@PathVariable Long storeId, @Valid @RequestBody OrderDTO dto){
		LOGGER.debug("create order with store id {}", storeId);
		dto.setStoreId(storeId);
		Long orderId = retailOrderService.createOrder(dto);
		return ResourceCreated.create(orderId, RestConstants.VERSION_ONE+"/orders/" + storeId);
	}
}
