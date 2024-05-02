package henryloadtesting.henryloadtesting.productserviceexecutor;

public class ProductEntity {
	private String name;
    private double price;
    private String desc;
    private int quantity;
    
    //Constructor
    
   
	public ProductEntity(String name, double price, String desc, int quantity) {
		super();
		this.name = name;
		this.price = price;
		this.desc = desc;
		this.quantity = quantity;
	}

	public ProductEntity() {
		
	}

	
	//Getter  and Setter
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
    
	

}
