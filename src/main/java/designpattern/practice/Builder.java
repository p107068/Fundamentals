package designpattern.practice;

public class Builder {	
	private static class Car {
		/*
		 * Builder's job is to construct the Car, and once construction is complete, you
		 * usually don't want the Car to be accidentally modified.
		 */
		private final String name;
		private final String desc;
	    private final int wheels;
	    private final int seats;
		
		Car(CarBuilder _builder) {
			this.name = _builder.name;
			this.wheels = _builder.wheels;
			this.seats = _builder.seats;
			this.desc = _builder.desc;
		}
	}
	
	private static class CarBuilder {
		String name;
		int wheels;
		int seats;
		String desc;
		
		CarBuilder withName(String _name) {
			this.name = _name;
			return this;
		}
		
		CarBuilder withDescription(String _desc) {
			this.desc = _desc;
			return this;
		}
		
		CarBuilder withWheels(int _wheels) {
			this.wheels = _wheels;
			return this;
		}
		
		CarBuilder withSeats(int _seats) {
			this.seats = _seats;
			return this;
		}
		
		Car build() {
			return new Car(this);
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Car car = new CarBuilder()
				.withDescription("Launching Scoda Kylaq this march 2026")
				.withName("Scoda Kylq")
				.withWheels(4)
				.build();
		System.out.println(car.name);
	}

}
