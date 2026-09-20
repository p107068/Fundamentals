package designpattern.tier3;

/**
 * Adapter Pattern
 *
 * Intent: Convert one interface into another interface that a client expects.
 *
 * Use when: Existing or third-party code has an incompatible API.
 *
 * Examples: Wrapping a legacy payment gateway; converting XML data to JSON; adapting
 * an old logging library to a new application interface.
 *
 * Structure: The adapter implements the target interface and delegates to the adaptee.
 *
 * Benefit: Reuses existing code without changing either client or adaptee.
 *
 * Tradeoff: Adds an extra layer that can hide API mismatches.
 */
public final class AdapterPatternExample {
    private AdapterPatternExample() {
    }

    public interface PaymentProcessor {
        void pay(double amount);
    }

    public static final class LegacyBankApi {
        public void makePaymentInPaise(long amountInPaise) {
            System.out.println("Legacy bank received " + amountInPaise + " paise");
        }
    }

    public static final class BankApiAdapter implements PaymentProcessor {
        private final LegacyBankApi bankApi;

        public BankApiAdapter(LegacyBankApi bankApi) {
            this.bankApi = bankApi;
        }

        @Override
        public void pay(double amount) {
            bankApi.makePaymentInPaise(Math.round(amount * 100));
        }
    }

    public static void main(String[] args) {
        PaymentProcessor processor = new BankApiAdapter(new LegacyBankApi());
        processor.pay(99.50);
    }
}