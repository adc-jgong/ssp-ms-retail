package com.tenx.ms.retail.stock;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.TestConstants;
import com.tenx.ms.retail.products.domain.ProductEntity;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.stock.rest.dto.StockDTO;
import com.tenx.ms.retail.store.domain.StoreEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=RetailServiceApp.class)
public class StockControllerTests extends AbstractIntegrationTest{
	private static final String REQUEST_URI = "%s/%s/stock/";
	private static final Long STOREID = 1L;
	private static final Long PRODUCTID = 1L;
	
	private final RestTemplate template = new TestRestTemplate();
	
	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("classpath:stockTests/stock-post-request.json")
	private File stockPostRequest;
	
	@Test
	public void testAddStock() {		 
		try {
			getJSONResponse(template,
					String.format(REQUEST_URI + "%s/%s/", basePath(), TestConstants.API_VERSION_1, STOREID, PRODUCTID),
					FileUtils.readFileToString(stockPostRequest),
					HttpMethod.POST);			
			StockDTO jsonStock = objectMapper.readValue(FileUtils.readFileToString(stockPostRequest), StockDTO.class);
			StoreEntity store = new StoreEntity();
			store.setStoreId(STOREID);
			ProductEntity product = new ProductEntity();
			product.setProductId(PRODUCTID);			
			Optional<StockEntity> dbStock = stockRepository.findByStoreAndProduct(store, product);
			Assert.assertSame(jsonStock.getQuantity(), dbStock.get().getProductCnt());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
