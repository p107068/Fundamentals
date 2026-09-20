package designpattern.tier2;

/**
 * State Pattern
 *
 * Intent: Change an object's behavior when its internal state changes.
 *
 * Use when: The object has state-dependent rules and large if-else or switch blocks
 * are becoming difficult to maintain.
 *
 * Examples: A vending machine; an order moving from placed to shipped; a document
 * moving from draft to review to published.
 *
 * Structure: The context (VendingMachine) delegates actions to its current State.
 * Each concrete state decides the next state and the valid behavior.
 *
 * Benefit: State-specific behavior is separated into small, focused classes.
 *
 * Tradeoff: Adds classes for each state and can be excessive for very simple state logic.
 */
public final class StatePatternExample {
    private StatePatternExample() {
    }

    public interface VendingMachineState {
        void insertMoney(VendingMachine machine);

        void selectItem(VendingMachine machine);
    }

    public static final class IdleState implements VendingMachineState {
        @Override
        public void insertMoney(VendingMachine machine) {
            System.out.println("Money accepted");
            machine.setState(new HasMoneyState());
        }

        @Override
        public void selectItem(VendingMachine machine) {
            System.out.println("Insert money first");
        }
    }

    public static final class HasMoneyState implements VendingMachineState {
        @Override
        public void insertMoney(VendingMachine machine) {
            System.out.println("Money already inserted");
        }

        @Override
        public void selectItem(VendingMachine machine) {
            System.out.println("Item selected; dispensing");
            machine.setState(new DispensingState());
        }
    }

    public static final class DispensingState implements VendingMachineState {
        @Override
        public void insertMoney(VendingMachine machine) {
            System.out.println("Please wait");
        }

        @Override
        public void selectItem(VendingMachine machine) {
            System.out.println("Already dispensing");
            machine.setState(new IdleState());
        }
    }

    public static final class VendingMachine {
        private VendingMachineState state = new IdleState();

        public void setState(VendingMachineState state) {
            this.state = state;
        }

        public void insertMoney() {
            state.insertMoney(this);
        }

        public void selectItem() {
            state.selectItem(this);
        }
    }

    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();
        machine.selectItem();
        machine.insertMoney();
        machine.selectItem();
        machine.selectItem();
    }
}