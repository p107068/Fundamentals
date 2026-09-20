package solid;

/** Demonstrates that a class should have one reason to change. */
public final class SingleResponsibilityExample {
    private SingleResponsibilityExample() {
    }

    public static final class Invoice {
        private final double amount;

        public Invoice(double amount) {
            this.amount = amount;
        }

        public double amount() {
            return amount;
        }
    }

    public static final class InvoiceCalculator {
        public double total(Invoice invoice) {
            return invoice.amount() * 1.20;
        }
    }

    public interface InvoiceRepository {
        void save(Invoice invoice);
    }

    public static final class ConsoleInvoiceRepository implements InvoiceRepository {
        @Override
        public void save(Invoice invoice) {
            System.out.println("Saved invoice: " + invoice.amount());
        }
    }

    public static void main(String[] args) {
        Invoice invoice = new Invoice(100.0);
        System.out.println(new InvoiceCalculator().total(invoice));
        new ConsoleInvoiceRepository().save(invoice);
    }
}