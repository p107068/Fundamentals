package designpattern.tier2;

/**
 * Decorator Pattern
 *
 * Intent: Add behavior or responsibilities to an object dynamically.
 *
 * Use when: Features should be combined flexibly without creating a subclass for
 * every possible combination.
 *
 * Examples: Coffee with milk and sugar; text streams with buffering or compression;
 * web requests with logging, authentication, or retry behavior.
 *
 * Structure: A decorator implements the same interface as the wrapped object and
 * adds behavior before or after delegating to it.
 *
 * Benefit: Behaviors can be stacked at runtime, such as Coffee -> Milk -> Sugar.
 *
 * Tradeoff: Many small wrapper objects can make debugging and object setup harder.
 */
public final class DecoratorPatternExample {
    private DecoratorPatternExample() {
    }

    public interface Coffee {
        String description();

        double cost();
    }

    public static final class SimpleCoffee implements Coffee {
        @Override
        public String description() {
            return "Coffee";
        }

        @Override
        public double cost() {
            return 50.0;
        }
    }

    public static abstract class CoffeeDecorator implements Coffee {
        protected final Coffee coffee;

        protected CoffeeDecorator(Coffee coffee) {
            this.coffee = coffee;
        }
    }

    public static final class MilkDecorator extends CoffeeDecorator {
        public MilkDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public String description() {
            return coffee.description() + ", Milk";
        }

        @Override
        public double cost() {
            return coffee.cost() + 10.0;
        }
    }

    public static final class SugarDecorator extends CoffeeDecorator {
        public SugarDecorator(Coffee coffee) {
            super(coffee);
        }

        @Override
        public String description() {
            return coffee.description() + ", Sugar";
        }

        @Override
        public double cost() {
            return coffee.cost() + 5.0;
        }
    }

    public static void main(String[] args) {
        Coffee coffee = new SimpleCoffee();
        coffee = new MilkDecorator(coffee);
        coffee = new SugarDecorator(coffee);

        System.out.println(coffee.description() + " = " + coffee.cost());
    }
}