package designpattern.practice;

public class Facade {
	
	private static class Product {
		private String name;
		private int amount;

		public Product(String name, int amount) {
			// TODO Auto-generated constructor st
			this.name = name;
			this.amount = amount;
		}		
	}
	
	private static class InventoryService {
		public InventoryService(Product product) {
			// TODO Auto-generated constructor stub
		}
		public boolean isProductAvailable(Product product) {
			// TODO Auto-generated method stub
			return true;
		}		
	}
	
	private static class PaymentService {
		public PaymentService() {
			// TODO Auto-generated constructor stub
		}

		public void placePayment(int amount) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private static class ShippingService {
		public ShippingService() {
			// TODO Auto-generated constructor stub
		}

		public void ship(Product product) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private static class OrderFacade {
		Product product;
		OrderFacade(Product _product) {
			product = _product;			
		}
		InventoryService inventoryService = new InventoryService(product);
		PaymentService paymentService = new PaymentService();
		ShippingService shippingService = new ShippingService();
		
		public void placeOrder() {
			if (inventoryService.isProductAvailable(product)) {
				paymentService.placePayment(product.amount);
				shippingService.ship(product);
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Product product = new Product("Lords of the ring", 100);
		OrderFacade facade = new OrderFacade(product);
		facade.placeOrder();
	}

}
