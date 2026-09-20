package designpattern.tier3;

/**
 * Facade Pattern
 *
 * Intent: Provide one simple interface to a complex set of subsystems.
 *
 * Use when: A client should not need to coordinate many lower-level services.
 *
 * Examples: Placing an order; starting a home theatre; a library API over many modules.
 *
 * Structure: The facade coordinates calls to multiple subsystem classes.
 *
 * Benefit: Simplifies client code and reduces coupling to subsystem details.
 *
 * Tradeoff: The facade can become too large if it tries to expose every subsystem feature.
 */
public final class FacadePatternExample {
    private FacadePatternExample() {
    }

    public static final class InventoryService {
        public boolean isAvailable(String product) {
        	// db query for product
            System.out.println("Checking inventory for " + product);
            return true;
        }
    }

    public static final class PaymentService {
        public void charge(double amount) {
            System.out.println("Charging " + amount);
        }
    }

    public static final class ShippingService {
        public void ship(String product) {
            System.out.println("Shipping " + product);
        }
    }

    public static final class OrderFacade {
        private final InventoryService inventory = new InventoryService();
        private final PaymentService payment = new PaymentService();
        private final ShippingService shipping = new ShippingService();

        public void placeOrder(String product, double amount) {
            if (inventory.isAvailable(product)) {
                payment.charge(amount);
                shipping.ship(product);
            }
        }
    }

    public static void main(String[] args) {
        new OrderFacade().placeOrder("Book", 499.0);
    }
}