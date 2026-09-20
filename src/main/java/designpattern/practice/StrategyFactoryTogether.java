package designpattern.practice;

public class StrategyFactoryTogether {
	
	private static enum PaymentMode {
		CASH, CARD, UPI
	}
	
	private static interface PaymentStrategy {
		void pay();
	}
	
	private static class UPIPaymentStrategy implements PaymentStrategy {
		@Override
		public void pay() {
			// TODO Auto-generated method stub
			System.out.println("UPI payment initiated!");
		}		
	}
	
	private static class CashPaymentStrategy implements PaymentStrategy {
		@Override
		public void pay() {
			// TODO Auto-generated method stub
			System.out.println("Cash payment initiated!");
		}		
	}
	
	private static class CardPaymentStrategy implements PaymentStrategy {
		@Override
		public void pay() {
			// TODO Auto-generated method stub
			System.out.println("Card payment initiated!");
		}		
	}
	
	private static class PaymentFactory {
		private PaymentStrategy create(PaymentMode _mode) {
			if (_mode == PaymentMode.CARD) {
				return new CardPaymentStrategy();
			} else if (_mode == PaymentMode.UPI) {
				return new UPIPaymentStrategy();
			} else {
				return new CashPaymentStrategy();
			}
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
		PaymentStrategy strategy = new PaymentFactory().create(PaymentMode.UPI);
		Payment payment = new Payment(strategy);
		payment.performPayment();
	}

}
