package designpattern.tier1;

/**
 * Strategy Pattern
 *
 * Intent: Put interchangeable algorithms or behaviors behind a common interface.
 *
 * Use when: The same operation can be performed in multiple ways, such as payment,
 * pricing, parking, or routing.
 *
 * Examples: Credit card, UPI, or cash payment; standard or premium pricing;
 * Google Maps or fastest-route selection.
 *
 * Structure: The context (Payment) uses a Strategy (PaymentStrategy); concrete
 * strategies (CreditCardPayment, UpiPayment, and CashPayment) provide the behavior.
 *
 * Benefit: Add or change a behavior without changing the context.
 *
 * Tradeoff: Creates more small classes and the caller must choose a strategy.
 */
public final class StrategyPatternExample {
    private StrategyPatternExample() {
    }

    public interface PaymentStrategy {
        void pay(double amount);
    }

    public static final class CreditCardPayment implements PaymentStrategy {
        @Override
        public void pay(double amount) {
            System.out.println("Paid " + amount + " with a credit card");
        }
    }

    public static final class UpiPayment implements PaymentStrategy {
        @Override
        public void pay(double amount) {
            System.out.println("Paid " + amount + " with UPI");
        }
    }

    public static final class CashPayment implements PaymentStrategy {
        @Override
        public void pay(double amount) {
            System.out.println("Paid " + amount + " with cash");
        }
    }

    public static final class Payment {
        private final PaymentStrategy strategy;

        public Payment(PaymentStrategy strategy) {
            this.strategy = strategy;
        }

        public void pay(double amount) {
            strategy.pay(amount);
        }
    }

    public static void main(String[] args) {
        new Payment(new CreditCardPayment()).pay(500.0);
        new Payment(new UpiPayment()).pay(250.0);
        new Payment(new CashPayment()).pay(100.0);
    }
}