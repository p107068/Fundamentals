package designpattern.practice;

public class Strategy {
	
	private static interface PaymentStrategy {
		void pay();
	}
	
	private static class UPIPayment implements PaymentStrategy {
		@Override
		public void pay() {
			System.out.println("UPI Payment initiated!");
		}		
	}
	
	private static class CardPayment implements PaymentStrategy {
		@Override
		public void pay() {
			System.out.println("Card Payment initiated!");
		}		
	}
	
	private static class Payment {
		PaymentStrategy strategy;
		
		Payment(PaymentStrategy _strategy) {
			strategy = _strategy;
		}
		
		void performPayment() {
			strategy.pay();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Payment(new UPIPayment()).performPayment();
	}

}
