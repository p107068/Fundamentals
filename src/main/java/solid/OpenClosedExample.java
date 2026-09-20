package solid;

/** Demonstrates code that is open for new behavior and closed for modification. */
public final class OpenClosedExample {
    private OpenClosedExample() {
    }

    public interface DiscountPolicy {
        double apply(double price);
    }

    public static final class NoDiscount implements DiscountPolicy {
        @Override
        public double apply(double price) {
            return price;
        }
    }

    public static final class PremiumDiscount implements DiscountPolicy {
        @Override
        public double apply(double price) {
            return price * 0.90;
        }
    }

    public static final class Checkout {
        public double finalPrice(double price, DiscountPolicy policy) {
            return policy.apply(price);
        }
    }

    public static void main(String[] args) {
        Checkout checkout = new Checkout();
        System.out.println(checkout.finalPrice(100.0, new PremiumDiscount()));
    }
}