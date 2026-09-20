package designpattern.practice;

public class Decorator {
	
	private interface Beverage {
		int cost();
		String description();
	}
	
	private static abstract class Decorator_ implements Beverage {
		Beverage beverage;
		
		Decorator_(Beverage _bev) {
			beverage = _bev;
		}
		
		public int cost() {
			return beverage.cost();
		}
		
		public String description() {
			return beverage.description();
		}
	}
	
	private static class MilkDecorator extends Decorator_ {

		MilkDecorator(Beverage _bev) {
			super(_bev);
		}

		@Override
		public int cost() {
			// TODO Auto-generated method stub
			return super.cost() + 10;
		}

		@Override
		public String description() {
			// TODO Auto-generated method stub
			return super.description() + ", Milk";
		}		
	}
	
	private static class SugarDecorator extends Decorator_ {
		SugarDecorator(Beverage _bev) {
			super(_bev);
			// TODO Auto-generated constructor stub
		}

		@Override
		public int cost() {
			// TODO Auto-generated method stub
			return super.cost() + 5;
		}

		@Override
		public String description() {
			// TODO Auto-generated method stub
			return super.description() + ", Sugar";
		}	
	}
	
	private static class Tea implements Beverage {

		@Override
		public int cost() {
			// TODO Auto-generated method stub
			return 50;
		}

		@Override
		public String description() {
			// TODO Auto-generated method stub
			return "Tea";
		}		
	}
	
	private static class Coffee implements Beverage {
		@Override
		public int cost() {
			// TODO Auto-generated method stub
			return 100;
		}

		@Override
		public String description() {
			// TODO Auto-generated method stub
			return "Coffee";
		}		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Beverage bev = new Coffee();
		bev = new MilkDecorator(bev);
		bev = new SugarDecorator(bev);		
		System.out.println(bev.description() + " = " + bev.cost());
		
		Beverage bev2 = new Tea();
		bev2 = new MilkDecorator(bev2);
		System.out.println(bev2.description() + " = " + bev2.cost());
	}

}
