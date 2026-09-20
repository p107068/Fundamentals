package designpattern.practice;

public class Factory {
	private static enum PaymentMode {
		CARD, UPI, CASH
	}
	
	private static interface Payment {
		public void pay();
	}
	
	private static class UPIPayment implements Payment {
		@Override
		public void pay() {
			// TODO Auto-generated method stub
			System.out.println("UPI payment initiated!");
		}		
	}
	
	private static class CashPayment implements Payment {
		@Override
		public void pay() {
			// TODO Auto-generated method stub
			System.out.println("Cash payment initiated!");
		}		
	}
	
	private static class CardPayment implements Payment {
		@Override
		public void pay() {
			// TODO Auto-generated method stub
			System.out.println("Card payment initiated!");
		}		
	}
	
	private static class PaymentFactory {
		
		Payment create(PaymentMode mode) {
			Payment payment;
			if (mode == PaymentMode.UPI) {
				payment = new UPIPayment();
			} else if (mode == PaymentMode.CARD) {
				payment = new CardPayment();
			} else {
				payment = new CashPayment();
			}
			return payment;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new PaymentFactory().create(PaymentMode.CARD).pay();
		new PaymentFactory().create(PaymentMode.CASH).pay();
	}

}
