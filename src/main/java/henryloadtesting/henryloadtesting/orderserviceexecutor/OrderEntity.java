package henryloadtesting.henryloadtesting.orderserviceexecutor;

public class OrderEntity {
	
	private String products;
    private String customerName;
    
    public OrderEntity() {
		
    }
    
	public OrderEntity(String products, String customerName) {
		super();
		this.products = products;
		this.customerName = customerName;
	}
	
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

    

}
