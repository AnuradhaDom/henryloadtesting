package henryloadtesting.henryloadtesting.userserviceexecutor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;


public class CreateUserExecutorService {

	public static final org.slf4j.Logger logger = LoggerFactory.getLogger(CreateUserExecutorService.class);

	public void executeUserLoadTesting() {
        try {
            // Step 1: Read JSON input from file
            ObjectMapper objectMapper = new ObjectMapper();
            UserEntity user = objectMapper.readValue(new File("apiInput/createUsers.json"), UserEntity.class);

            // Step 2: Extract user details
            //String originalUserName = user.getUserName();
            //String originalEmail = user.getEmail();
            String password = user.getPassword();
            boolean active = user.isActive();
            String roles = user.getRoles();

            // Step 3: Generate 100 unique JSON inputs
            List<UserEntity> users = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                String modifiedName = "User" + i;
                String modifiedEmail = "user" + UUID.randomUUID() + "@example.com";
                UserEntity modifiedUser = new UserEntity(modifiedName, modifiedEmail, password, active, roles);
                users.add(modifiedUser);
            }

         // Step 4: Execute load test using ExecutorService
            ExecutorService executorService = Executors.newFixedThreadPool(10); // Adjust the thread pool size as needed
            for (UserEntity modifiedUser : users) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        sendRequest(modifiedUser);
                    }
                });
            }

            // Shutdown the executor after all tasks are complete
            executorService.shutdown();

        } catch (IOException e) {
            logger.error("Error reading JSON input file: {}", e.getMessage());
        }
    }

    private static void sendRequest(UserEntity user) {
        try {
            // Step 5: Send request to REST API
            String endpoint = "http://localhost:8090/v1/adduser";
            RestTemplate restTemplate = new RestTemplate();

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create request entity with user details
            HttpEntity<UserEntity> requestEntity = new HttpEntity<>(user, headers);

            // Send POST request to REST API
            ResponseEntity<String> responseEntity = restTemplate.exchange(endpoint, HttpMethod.POST, requestEntity, String.class);

            // Log response status code
            HttpStatus statusCode = responseEntity.getStatusCode();
            logger.info("Response received for user: {}, Status Code: {}", user.getUserName(), statusCode);

        } catch (Exception e) {
            logger.error("Error sending request for user: {}", user.getUserName(), e);
        }
    }
}
