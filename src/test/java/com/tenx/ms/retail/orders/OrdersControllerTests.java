package com.tenx.ms.retail.orders;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
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
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.TestConstants;
import com.tenx.ms.retail.order.domain.CustomerOrderEntity;
import com.tenx.ms.retail.order.domain.OrderItemEntity;
import com.tenx.ms.retail.order.repository.OrderItemRepository;
import com.tenx.ms.retail.order.repository.OrderRepository;
import com.tenx.ms.retail.order.rest.dto.OrderDTO;
import com.tenx.ms.retail.order.rest.dto.OrderProductDTO;
import com.tenx.ms.retail.products.domain.ProductEntity;
import com.tenx.ms.retail.products.repository.ProductRepository;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.store.domain.StoreEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=RetailServiceApp.class)
public class OrdersControllerTests extends AbstractIntegrationTest{
	private static final String REQUEST_URI = "%s/%s/orders/";
	private static final Long STOREID = 1L;
	
	private final RestTemplate template = new TestRestTemplate();
	
	@Value("classpath:orderTests/order-post-request.json")
	private File orderPostRequest;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private OrderRepository orderRepository;	 
	
	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void testPlaceOrder() {	
		try {
			String request = FileUtils.readFileToString(orderPostRequest);
			OrderDTO orderDto = objectMapper.readValue(request, OrderDTO.class);
			StoreEntity store = new StoreEntity();
			store.setStoreId(STOREID);
			Map<Long, Integer> stockMap = new HashMap<Long, Integer>();
			Map<Long, Integer> orderMap = new HashMap<Long, Integer>();
			for (OrderProductDTO orderProduct : orderDto.getProducts()) {
				Long productId = orderProduct.getProductId();
				ProductEntity product = productRepository.findOne(productId);				
				Optional<StockEntity> stock = stockRepository.findByStoreAndProduct(store, product);
				stockMap.put(productId, stock.get().getProductCnt());
				orderMap.put(productId, orderProduct.getQuantity());
			}
			ResponseEntity<String> response = getJSONResponse(template,
					String.format(REQUEST_URI+"%s/", basePath(), TestConstants.API_VERSION_1, STOREID),
					request,
					HttpMethod.POST);
			ResourceCreated<Number> idResource = objectMapper.readValue(response.getBody(), ResourceCreated.class);
			Long orderId = idResource.getId().longValue();
			CustomerOrderEntity order = orderRepository.findOne(orderId);
			List<OrderItemEntity> items = orderItemRepository.findItemByOrder(order);
			for (OrderItemEntity item : items) {
				Long productId = item.getStock().getProduct().getProductId();
				Integer original = stockMap.get(productId);
				Integer sales = orderMap.get(productId);
				Integer left = item.getStock().getProductCnt();
				assertSame("stock is not match", original-sales, left);
			}			
		} catch (IOException e) {
			fail(e.getMessage());
		}		
	}	
}
