package designpattern.tier3;

/**
 * Chain of Responsibility Pattern
 *
 * Intent: Pass a request through a chain until one handler processes it.
 *
 * Use when: Multiple handlers may process a request and the sender should not choose one.
 *
 * Examples: Expense approval; support escalation; servlet filters; authentication checks.
 *
 * Structure: Each handler handles the request or forwards it to the next handler.
 *
 * Benefit: Decouples the request sender from concrete handlers and supports flexible chains.
 *
 * Tradeoff: A request can reach the end of the chain without being handled.
 */
public final class ChainOfResponsibilityPatternExample {
    private ChainOfResponsibilityPatternExample() {
    }

    public static abstract class ExpenseApprover {
        private ExpenseApprover next;

        public ExpenseApprover setNext(ExpenseApprover next) {
            this.next = next;
            return next;
        }

        public void approve(double amount) {
            if (canApprove(amount)) {
                System.out.println(role() + " approved " + amount);
            } else if (next != null) {
                next.approve(amount);
            } else {
                System.out.println("Expense requires board approval: " + amount);
            }
        }

        protected abstract boolean canApprove(double amount);

        protected abstract String role();
    }

    public static final class TeamLead extends ExpenseApprover {
        @Override
        protected boolean canApprove(double amount) {
            return amount <= 1_000;
        }

        @Override
        protected String role() {
            return "Team lead";
        }
    }

    public static final class Manager extends ExpenseApprover {
        @Override
        protected boolean canApprove(double amount) {
            return amount <= 10_000;
        }

        @Override
        protected String role() {
            return "Manager";
        }
    }

    public static void main(String[] args) {
        ExpenseApprover teamLead = new TeamLead();
        teamLead.setNext(new Manager());

        teamLead.approve(500.0);
        teamLead.approve(5_000.0);
        teamLead.approve(50_000.0);
    }
}