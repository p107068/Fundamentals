package designpattern.tier1;

/**
 * Factory Method and Abstract Factory
 *
 * Factory Method intent: Let a subclass decide which product to create. Here,
 * Restaurant creates a Meal and PizzaRestaurant chooses Pizza.
 *
 * Abstract Factory intent: Create a family of related objects without naming their
 * concrete classes. Here, UiFactory creates matching Button and CheckBox products.
 *
 * Use when: Creation varies by subclass, theme, platform, or product family.
 *
 * Examples: Restaurant subclasses creating different meals; LightUiFactory or
 * DarkUiFactory creating matching buttons and checkboxes; Windows or Mac UI widgets.
 *
 * Tradeoff: These patterns add interfaces and classes, so use them when creation
 * complexity or variation justifies that structure.
 */
public final class FactoryMethodAbstractFactoryExample {
    private FactoryMethodAbstractFactoryExample() {
    }

    // Factory Method: each restaurant decides which meal it creates.
    public interface Meal {
        void serve();
    }

    public static final class Pizza implements Meal {
        @Override
        public void serve() {
            System.out.println("Serving pizza");
        }
    }

    public static final class Burger implements Meal {
        @Override
        public void serve() {
            System.out.println("Serving burger");
        }
    }

    public static abstract class Restaurant {
        protected abstract Meal createMeal();

        public void serveMeal() {
            createMeal().serve();
        }
    }

    public static final class PizzaRestaurant extends Restaurant {
        @Override
        protected Meal createMeal() {
            return new Pizza();
        }
    }

    // Abstract Factory: creates matching buttons and checkboxes for one theme.
    public interface Button {
        void render();
    }

    public interface CheckBox {
        void render();
    }

    public interface UiFactory {
        Button createButton();

        CheckBox createCheckBox();
    }

    public static final class LightButton implements Button {
        @Override
        public void render() {
            System.out.println("Rendering a light button");
        }
    }

    public static final class LightCheckBox implements CheckBox {
        @Override
        public void render() {
            System.out.println("Rendering a light checkbox");
        }
    }

    public static final class LightUiFactory implements UiFactory {
        @Override
        public Button createButton() {
            return new LightButton();
        }

        @Override
        public CheckBox createCheckBox() {
            return new LightCheckBox();
        }
    }

    public static void main(String[] args) {
        new PizzaRestaurant().serveMeal();

        UiFactory factory = new LightUiFactory();
        factory.createButton().render();
        factory.createCheckBox().render();
    }
}