package com.tenx.ms.retail.store.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.RetailBaseController;
import com.tenx.ms.retail.exception.RetailDeleteConstraintException;
import com.tenx.ms.retail.exception.UniqueKeyViolationException;
import com.tenx.ms.retail.store.rest.dto.StoreDTO;
import com.tenx.ms.retail.store.service.StoreService;

@Api(value="api endpoint for create, list and get by id for store")
@RestController
@RequestMapping(RestConstants.VERSION_ONE + "/stores")
public class StoreController extends RetailBaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(StoreController.class);
	@Autowired
	private StoreService storeService;
	
	@ApiOperation(value="create a new store info.")
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Create Store Successful."),
	        @ApiResponse(code = 412, message = "Payload for Store is not correct"),
	        @ApiResponse(code = 500, message = "Internal server error")})
	@RequestMapping(method = RequestMethod.POST, consumes={"application/json"})
	@ResponseStatus(HttpStatus.OK)	
	@ResponseBody
	public ResponseEntity<ResourceCreated<Long>> createStore(@Valid @RequestBody StoreDTO dto, HttpServletRequest request) {		
		LOGGER.debug("POST create new store with store name {}", dto.getStoreName() );
		try {
			Long id = storeService.createStore(dto);
			dto.setStoreId(id);
			return ResourceCreated.create(id, RestConstants.VERSION_ONE + "/stores");
		} catch (DataIntegrityViolationException e) {			
			UniqueKeyViolationException ex = new UniqueKeyViolationException("Store name "+ dto.getStoreName() + " already exists.", e.getRootCause());
			ex.initCause(e);
			throw ex;
		}
	}
	
	
	@ApiOperation("delete the store with store id")
	@ApiResponses({@ApiResponse(code=200, message="delete store sucessfull"), 
		@ApiResponse(code=500, message="service internal error")})
	@RequestMapping(value="/{storeId:\\d+}", method=RequestMethod.DELETE)
	public void deleteStore(@PathVariable Long storeId, HttpServletRequest request) throws RetailDeleteConstraintException{
		storeService.deleteStore(storeId);		
	}
	
	@ApiOperation(value="create a new store info.")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Create Successful."),
	        @ApiResponse(code = 404, message = "Data is not correct"),
	        @ApiResponse(code = 500, message = "Internal server error")})
	@RequestMapping(value="/{storeId:\\d+}", method = RequestMethod.GET)	
	public StoreDTO getStoreById(@ApiParam(name = "storeId", value = "get store by Id")
			@PathVariable Long storeId) {
		LOGGER.debug("GET store info by id {}", storeId );
		return storeService.getStoreById(storeId);
	}
	
	@ApiOperation(value="create a new store info.")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Create Successful."),	        
	        @ApiResponse(code = 500, message = "Internal server error")})
	@RequestMapping(method=RequestMethod.GET)
	public Paginated<StoreDTO> getAllStore(Pageable pageable) {
		LOGGER.debug("GET store list" );
		return storeService.getAllStores(pageable, RestConstants.VERSION_ONE + "/stores");
	}
}
