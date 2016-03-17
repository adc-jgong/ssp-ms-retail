package com.tenx.ms.retail.product;

import static org.junit.Assert.fail;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.rest.RestConstants;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.TestConstants;
import com.tenx.ms.retail.products.domain.ProductEntity;
import com.tenx.ms.retail.products.repository.ProductRepository;
import com.tenx.ms.retail.products.rest.dto.ProductDTO;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.store.domain.StoreEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=RetailServiceApp.class)
public class ProductControllerTest extends AbstractIntegrationTest {
	private static final String REQUEST_URI = "%s/%s/products/";	
	private final RestTemplate template = new TestRestTemplate();
	
	@Value("classpath:productTests/product-get-response.json")
	private File productGetResponse;
	
	@Value("classpath:productTests/product-post-request.json")
	private File productPostRequest;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
			
	@Test
	public void testGetAllProductsByStoreId() {
		Paginated<ProductDTO> productList = 
				template.getForObject(String.format(REQUEST_URI+"%s/", basePath(), TestConstants.API_VERSION_1, 1L)
						, Paginated.class);
		Assert.assertSame(productList.getContent().size(),3);
	}

	@Test
	public void testGetProductsByStoreId() {
		ResponseEntity<String> response = getJSONResponse(template,
				String.format(REQUEST_URI+"%s/"+"%s", basePath(), TestConstants.API_VERSION_1, 1L, 1L),
				null,
				HttpMethod.GET);
		String receive = response.getBody();
		try {
			String expect = FileUtils.readFileToString(productGetResponse);
			assertEquals(receive, expect, false);
		} catch (IOException | JSONException e) {
			fail(e.getMessage());
		} 	
	}
	
	@Test
	public void testCreateProduct() {
		try {			
			ResponseEntity<String> response = getJSONResponse(template, 
					String.format(REQUEST_URI+"%s/", basePath(), RestConstants.VERSION_ONE, 1L),
					FileUtils.readFileToString(productPostRequest),
					HttpMethod.POST);
			ResourceCreated<Number> idWrapper = objectMapper.readValue(response.getBody(), ResourceCreated.class);			
			Assert.assertNotSame(idWrapper.getId().intValue() , 0);
			StoreEntity store = new StoreEntity();
			store.setStoreId(1L);
			
			ProductEntity product = new ProductEntity();
			product.setProductId(idWrapper.getId().longValue());
			Optional<StockEntity> stockOptional = stockRepository.findByStoreAndProduct(store, product);
			stockRepository.delete(stockOptional.get());
			productRepository.delete(product);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
