package solid;

/** Demonstrates a service depending on a repository abstraction, not a database. */
public final class RepositoryDependencyInversionExample {
    private RepositoryDependencyInversionExample() {
    }

    public interface OrderRepository {
        void save(String orderId);
    }

    public static final class MySQLOrderRepository implements OrderRepository {
        @Override
        public void save(String orderId) {
            System.out.println("Saving order " + orderId + " in MySQL");
        }
    }

    public static final class MongoDBOrderRepository implements OrderRepository {
        @Override
        public void save(String orderId) {
            System.out.println("Saving order " + orderId + " in MongoDB");
        }
    }

    public static final class OrderService {
        private final OrderRepository repository;

        public OrderService(OrderRepository repository) {
            this.repository = repository;
        }

        public void placeOrder(String orderId) {
            System.out.println("Placing order " + orderId);
            repository.save(orderId);
        }
    }

    public static void main(String[] args) {
        OrderService mysqlService = new OrderService(new MySQLOrderRepository());
        mysqlService.placeOrder("mysql-1001");

        OrderService mongoService = new OrderService(new MongoDBOrderRepository());
        mongoService.placeOrder("mongo-1002");
    }
}