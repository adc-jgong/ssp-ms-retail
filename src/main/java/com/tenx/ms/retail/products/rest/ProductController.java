package com.tenx.ms.retail.products.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
import org.springframework.web.bind.annotation.RestController;

import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.retail.RetailBaseController;
import com.tenx.ms.retail.exception.RetailDeleteConstraintException;
import com.tenx.ms.retail.exception.UniqueKeyViolationException;
import com.tenx.ms.retail.products.rest.dto.ProductDTO;
import com.tenx.ms.retail.products.service.ProductService;

@Api(value="products api for add product listing products and get product detail")
@RestController
@RequestMapping(value=RestConstants.VERSION_ONE+"/products")
public class ProductController extends RetailBaseController {

	private final static Logger LOGGER =  LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService retailProductService;	
		
	@ApiOperation(value="post a new product")	
	@ApiResponses(value = {@ApiResponse(code=201, message="create product sucessfully"),
						   @ApiResponse(code=412, message="payload for create a product is not correct"),
						   @ApiResponse(code=404, message="resource for store id is not found"),
						   @ApiResponse(code=500, message="internal service error")})
	@RequestMapping(value="/{storeId:\\d+}", method=RequestMethod.POST, consumes={"application/json"})
	public ResponseEntity<ResourceCreated<Long>> createProduct(@Valid @RequestBody ProductDTO dto, @PathVariable Long storeId) {
		LOGGER.debug("create a new product with store id {}", storeId);
		try {
			dto.setStoreId(storeId);			
			Long productId = retailProductService.createProduct(dto);
			return ResourceCreated.create(productId, RestConstants.VERSION_ONE + "/products/" + storeId);
		} catch (DataIntegrityViolationException e) {
			UniqueKeyViolationException ex = new UniqueKeyViolationException("Product sku "+ dto.getSku() + " already exists", e.getRootCause());
			ex.initCause(e);
			throw ex;
		}
	}
	
	@ApiOperation(value="get product list with store id")
	@ApiResponses(value={@ApiResponse(code=200, message="get product list for a list sucessfully"),
			             @ApiResponse(code=404, message="resource for store id is not found"),
						 @ApiResponse(code=500, message="internal service error")})
	@RequestMapping(value="/{storeId:\\d+}", method=RequestMethod.GET)
	public Paginated<ProductDTO> getProductList(@PathVariable final Long storeId, Pageable pageable) {
		LOGGER.debug("get product list with store id {}", storeId);
		return retailProductService.getProductList(storeId, pageable, RestConstants.VERSION_ONE + "/products/" );
	}
	
	@ApiOperation(value="get product with store id and product id")
	@ApiResponses(value={@ApiResponse(code=200, message="get product detail sucessfally"), 
			             @ApiResponse(code=404, message="resource for store id is not found"),
						 @ApiResponse(code=500, message="internal service error")})
	@RequestMapping(value="/{storeId:\\d+}/{productId:\\d+}")
	public ProductDTO getProductDetail(@PathVariable final Long storeId, @PathVariable final Long productId) {
		LOGGER.debug("get product with store id {} and product id {}", storeId, productId);
		return retailProductService.getProductById(storeId, productId);
	}
	
	@ApiOperation(value="create product without store id") 
	@ApiResponses(value={@ApiResponse(code=201, message="create a product without store id sucessfully"), 
			@ApiResponse(code=412, message="validation error for input date"),
			@ApiResponse(code=500, message="internal service error")})
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<ResourceCreated<Long>> createProduct(@Valid @RequestBody ProductDTO dto) {
		LOGGER.debug("create a new product");		
		
		Long productId = retailProductService.createProduct(dto);
		return ResourceCreated.create(productId, RestConstants.VERSION_ONE + "/products/" );
	}

	@ApiOperation(value="get product list") 
	@ApiResponses(value={@ApiResponse(code=200, message="get full product list."),
			@ApiResponse(code=500, message="internal service error")})
	@RequestMapping(method=RequestMethod.GET)
	public Paginated<ProductDTO> getAllProducts(Pageable pageable) {
		LOGGER.debug("create a new product");		
		return retailProductService.getAllProducts(pageable, RestConstants.VERSION_ONE+"/products/");
	}
	
	@ApiOperation("delete product")
	@ApiResponses({@ApiResponse(code=200, message="delete sucessfully"), 
			@ApiResponse(code=500, message="internal service error")})
	@RequestMapping(value="/{productId:\\d+}", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteProduct(@PathVariable Long productId) throws RetailDeleteConstraintException{
		retailProductService.deleteProduct(productId);
		return new ResponseEntity<String>("delete sucessfully", HttpStatus.OK);
	}
	
	@ApiOperation("delete product")
	@ApiResponses({@ApiResponse(code=200, message="delete sucessfully"), 
			@ApiResponse(code=500, message="internal service error")})
	@RequestMapping(value="/{storeId:\\d+}/{productId:\\d+}", method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteProduct(@PathVariable Long storeId, @PathVariable Long productId) throws RetailDeleteConstraintException{
		retailProductService.deleteProduct(productId);
		return new ResponseEntity<String>("delete sucessfully", HttpStatus.OK);
	}
}
