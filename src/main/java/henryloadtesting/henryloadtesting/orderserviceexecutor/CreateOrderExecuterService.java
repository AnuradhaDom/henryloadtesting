package henryloadtesting.henryloadtesting.orderserviceexecutor;

import com.fasterxml.jackson.databind.ObjectMapper;

import henryloadtesting.henryloadtesting.productserviceexecutor.CreateProductExecuterService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CreateOrderExecuterService {

    private static final Logger logger = LoggerFactory.getLogger(CreateOrderExecuterService.class);

    public void executeOrderLoadTesting() {
        try {
            // Step 1: Read JSON input from file
            ObjectMapper objectMapper = new ObjectMapper();
            OrderEntity orderInput = objectMapper.readValue(new File("apiInput/createOrderInput.json"), OrderEntity.class);

            // Step 2: Generate 100 unique inputs
            List<OrderEntity> orders = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
            /*    String productName;
                synchronized (CreateProductExecuterService.productNames) {
                    productName = CreateProductExecuterService.productNames.get(i % CreateProductExecuterService.productNames.size());
                    
                    logger.info("ProductNames in executeOrderLoadTesting: {}", productName);
               }
                String modifiedProducts = productName; 
                */
                String modifiedCustomerName = orderInput.getCustomerName();
                String modifiedCustomerProductsFromJson = orderInput.getProducts();

                OrderEntity modifiedOrderInput = new OrderEntity(modifiedCustomerProductsFromJson, modifiedCustomerName);
                orders.add(modifiedOrderInput);
                
             // Log modified input
                logger.info("Modified order input: {}", modifiedOrderInput);

            }
            // Step 3: Send data to REST API
            ExecutorService executorService = Executors.newFixedThreadPool(10); // Adjust the thread pool size as needed
            for (OrderEntity modifiedOrderInput : orders) {
                executorService.execute(() -> sendRequest(modifiedOrderInput));
            }

            // Shutdown the executor after all tasks are complete
            executorService.shutdown();

        } catch (IOException e) {
            logger.error("Error occurred while reading JSON input file: {}", e.getMessage());
        }
    }

    private static void sendRequest(OrderEntity orderInput) {
        try {
            // Step 4: Send request to REST API
            String endpoint = "http://localhost:8090/v1/order/createOrder";
            RestTemplate restTemplate = new RestTemplate();

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create request entity with order input details
            HttpEntity<OrderEntity> requestEntity = new HttpEntity<>(orderInput, headers);

            // Send POST request to REST API
            ResponseEntity<String> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, String.class);

            // Log response
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                logger.info("Request sent for order: {}", orderInput);
                logger.info("Response: {}", responseEntity.getBody());
            } else {
                logger.error("Error occurred while sending request for order: {}", orderInput);
            }

        } catch (Exception e) {
            logger.error("Exception occurred while sending request: {}", e.getMessage());
        }
    }
    
    
   
}

