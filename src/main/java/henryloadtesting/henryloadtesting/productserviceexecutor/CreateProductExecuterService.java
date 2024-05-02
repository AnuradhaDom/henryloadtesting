package henryloadtesting.henryloadtesting.productserviceexecutor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateProductExecuterService {
	
	private static final Logger logger = LoggerFactory.getLogger(CreateProductExecuterService.class);
	public static final List<String> productNames = new ArrayList<>();


    public void executeProductLoadTesting() {
        try {
            // Step 1: Read JSON input from file
            ObjectMapper objectMapper = new ObjectMapper();
            ProductEntity product = objectMapper.readValue(new File("apiInput/createProductInput.json"), ProductEntity.class);

            // Step 2: Generate 100 unique inputs
            List<ProductEntity> products = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                String modifiedName = product.getName() + i;
                double modifiedPrice = product.getPrice() + i;
                String modifiedDesc = product.getDesc() + " " + UUID.randomUUID().toString();
                int modifiedQuantity = product.getQuantity() + i;

                ProductEntity modifiedProduct = new ProductEntity(modifiedName, modifiedPrice, modifiedDesc, modifiedQuantity);
                products.add(modifiedProduct);
            }

            // Step 3: Send data to REST API
            ExecutorService executorService = Executors.newFixedThreadPool(10); // Adjust the thread pool size as needed
            for (ProductEntity modifiedProduct : products) {
                executorService.execute(() -> {
                    sendRequest(modifiedProduct);
                    synchronized (productNames) {
                        productNames.add(modifiedProduct.getName());
                        
                         }
                });
            }
            
            
            // Shutdown the executor after all tasks are complete
            executorService.shutdown();
            
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            logger.info("ProductNames in executeProductLoadTesting: {}", productNames);
        

        } catch (IOException  | InterruptedException e) {
            logger.error("Error occurred while reading JSON input file: {}", e.getMessage());
        }
    }

    private static void sendRequest(ProductEntity product) {
        try {
            // Step 4: Send request to REST API
            String endpoint = "http://localhost:8090/v1/products/addProduct";
            RestTemplate restTemplate = new RestTemplate();

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create request entity with product details
            HttpEntity<ProductEntity> requestEntity = new HttpEntity<>(product, headers);

            // Send POST request to REST API
            ResponseEntity<String> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, String.class);

            // Log response
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                logger.info("Request sent for product: {}", product.getName());
                logger.info("Response: {}", responseEntity.getBody());
            } else {
                logger.error("Error occurred while sending request for product: {}", product.getName());
            }

        } catch (Exception e) {
            logger.error("Exception occurred while sending request: {}", e.getMessage());
        }
    }

}
