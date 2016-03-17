package com.tenx.ms.retail.stock.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.retail.stock.rest.dto.StockDTO;
import com.tenx.ms.retail.stock.rest.dto.StockRequestDTO;
import com.tenx.ms.retail.stock.service.StockService;

@Api(value="api for stock related operations")
@RestController
@RequestMapping(value=RestConstants.VERSION_ONE+"/stock")
public class StockController {

	private final static Logger LOGGER = LoggerFactory.getLogger(StockController.class);
	
	@Autowired
	private StockService retailStockService;
	
	@ApiOperation(value="update stock with store id and product id")
	@ApiResponses(value={@ApiResponse(code=200, message="update stock sucessfully"), 
					 	 @ApiResponse(code=404, message="stock not found with the store id and product id"),
						 @ApiResponse(code=500, message="internal service error")})
	@RequestMapping(value="/{storeId:\\d+}/{productId:\\d+}", method=RequestMethod.POST, consumes={"application/json"})
	public StockDTO createUpdateStock(@RequestBody StockRequestDTO dto, @PathVariable Long storeId
			, @PathVariable Long productId) {		
		LOGGER.debug("update stock with store id {} and product id {}", storeId, productId);
		Integer newCnt = retailStockService.createUpdateStock(storeId, productId, dto.getQuantity());
		StockDTO stockDto = new StockDTO();
		stockDto.setProductId(productId);
		stockDto.setStockId(storeId);		
		stockDto.setQuantity(newCnt);
		return stockDto;
	}
}
