package solid;

/** Demonstrates small, role-specific interfaces instead of one large interface. */
public final class InterfaceSegregationExample {
    private InterfaceSegregationExample() {
    }

    public interface Workable {
        void work();
    }

    public interface Eatable {
        void eat();
    }

    public static final class Human implements Workable, Eatable {
        @Override
        public void work() {
            System.out.println("Human works");
        }

        @Override
        public void eat() {
            System.out.println("Human eats");
        }
    }

    public static final class Robot implements Workable {
        @Override
        public void work() {
            System.out.println("Robot works");
        }
    }

    public static void main(String[] args) {
        new Human().eat();
        new Robot().work();
    }
}