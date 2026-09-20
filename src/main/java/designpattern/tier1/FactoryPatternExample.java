package designpattern.tier1;

/**
 * Factory Pattern
 *
 * Intent: Encapsulate object creation and return an interface or parent type.
 *
 * Use when: The caller should request a type without knowing the construction details.
 *
 * Examples: VehicleFactory creating a Car, Bike, or Truck; notification factory
 * creating EmailNotification or SmsNotification; parser factory selecting JSON or XML.
 *
 * Structure: VehicleFactory creates Vehicle implementations such as Car, Bike, or Truck.
 *
 * Benefit: Keeps creation logic in one place and reduces coupling to concrete classes.
 *
 * Tradeoff: The factory can become large as the number of product types grows.
 */
public final class FactoryPatternExample {
    private FactoryPatternExample() {
    }

    public interface Vehicle {
        void drive();
    }

    public static final class Car implements Vehicle {
        @Override
        public void drive() {
            System.out.println("Driving a car");
        }
    }

    public static final class Bike implements Vehicle {
        @Override
        public void drive() {
            System.out.println("Riding a bike");
        }
    }

    public static final class Truck implements Vehicle {
        @Override
        public void drive() {
            System.out.println("Driving a truck");
        }
    }

    public static final class VehicleFactory {
        public Vehicle create(String type) {
            if ("car".equalsIgnoreCase(type)) {
                return new Car();
            }
            if ("bike".equalsIgnoreCase(type)) {
                return new Bike();
            }
            if ("truck".equalsIgnoreCase(type)) {
                return new Truck();
            }
            throw new IllegalArgumentException("Unknown vehicle: " + type);
        }
    }

    public static void main(String[] args) {
        VehicleFactory factory = new VehicleFactory();
        factory.create("car").drive();
        factory.create("bike").drive();
        factory.create("truck").drive();
    }
}