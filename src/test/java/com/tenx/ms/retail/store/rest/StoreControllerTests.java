package com.tenx.ms.retail.store.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenx.ms.commons.rest.dto.Paginated;
import com.tenx.ms.commons.rest.dto.ResourceCreated;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.TestConstants;
import com.tenx.ms.retail.store.repository.StoreRepository;
import com.tenx.ms.retail.store.rest.dto.StoreDTO;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
public class StoreControllerTests extends AbstractIntegrationTest {
	private static final String REQUEST_URI = "%s/%s/stores/";	
	private static final String STORE_NAME = "PUB 1";
	private final RestTemplate template = new TestRestTemplate();
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Value("classpath:storeTests/store-get-response.json")
	private File storeGetResponse;
	
	@Value("classpath:storeTests/store-post-request.json")
	private File storePostResponse;
	
	private StoreDTO createStore() {
		return new StoreDTO()	
		    .setStoreId(1L)
			.setStoreName(STORE_NAME);
	}
	
	@Test
	public void testCreateStore() {
		 try {
			ResponseEntity<String> response = getJSONResponse(template,
			         String.format(REQUEST_URI, basePath(), TestConstants.API_VERSION_1),
			         FileUtils.readFileToString(storePostResponse),
			         HttpMethod.POST);
			 ResourceCreated idObj = objectMapper.readValue(response.getBody(), ResourceCreated.class);
			 Long id = ((Number)(idObj.getId())).longValue();
			 assertTrue( "store create with id " + id, id > 0);
			 storeRepository.delete(id);
		} catch (IOException e) {
			fail(e.getMessage());
		} 
	}	
		
	@Test
	public void testGetStores() {
		Paginated<StoreDTO> storeDto = template.getForObject(String.format(REQUEST_URI, basePath(), TestConstants.API_VERSION_1), Paginated.class);
		Assert.assertSame(storeDto.getContent().size(), 3);			
	}
		
	@Test
	public void testGetStoreById() {		
		StoreDTO store = createStore();
		ResponseEntity<String> response = getJSONResponse(template
				, String.format(REQUEST_URI+"%s/", basePath(), TestConstants.API_VERSION_1, store.getStoreId())
				, null
				, HttpMethod.GET);
		try {
			String expected = FileUtils.readFileToString(storeGetResponse);
			String received = response.getBody();
			JSONAssert.assertEquals(expected, received, false);
			assertEquals("HTTP Status code incorrect", HttpStatus.OK, response.getStatusCode());
		} catch (IOException | JSONException e) {
			fail(e.getMessage());
		} 		
	}
	
	@Test
	public void testStoreByIdNotFound() {
		ResponseEntity<String> response = getJSONResponse(template, String.format(REQUEST_URI+"%s/", basePath(), TestConstants.API_VERSION_1, -1L)
				, null, HttpMethod.GET);
		assertEquals("HTTP Status Code incorrect", HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
