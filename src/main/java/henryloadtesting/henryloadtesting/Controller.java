package henryloadtesting.henryloadtesting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/getloadtesting")
public class Controller {

	@GetMapping
	public String getloadtesting() {
		 System.out.println("This is the Henryschein Load testing:");
		 
		 return "This is the Henryschein Load testing" ;
	}
}
