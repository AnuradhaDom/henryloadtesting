package henryloadtesting.henryloadtesting;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import henryloadtesting.henryloadtesting.orderserviceexecutor.CreateOrderExecuterService;
import henryloadtesting.henryloadtesting.productserviceexecutor.CreateProductExecuterService;
import henryloadtesting.henryloadtesting.userserviceexecutor.CreateUserExecutorService;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		System.out.println("This is the Henry LoadTesting ");
		
		       //UserService LoadTesting
				CreateUserExecutorService userService = new CreateUserExecutorService();
				userService.executeUserLoadTesting();
				
				//ProductService LoadTesting
				CreateProductExecuterService productService = new CreateProductExecuterService();
				CompletableFuture<Void> productFuture = CompletableFuture.runAsync(() -> productService.executeProductLoadTesting());
				
				// Wait for productService to complete before executing orderService
				try {
					productFuture.get(); // This will wait until productService execution completes
					System.out.println("ProductService Load Testing Completed. Starting OrderService Load Testing...");
					
					
					
					// Order LoadTesting
		
					CreateOrderExecuterService orderService = new CreateOrderExecuterService();
					orderService.executeOrderLoadTesting();
				
	
	
	  } catch (InterruptedException | ExecutionException e) {
	
					e.printStackTrace();
				}
				
				
	
				
	} 

}