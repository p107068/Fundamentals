package designpattern.tier3;

/**
 * Prototype Pattern
 *
 * Intent: Create new objects by copying an existing prototype.
 *
 * Use when: Creating an object is expensive or many objects begin with the same setup.
 *
 * Examples: Copying game characters; duplicating configured documents; creating templates.
 *
 * Structure: The prototype exposes a copy operation that returns an independent object.
 *
 * Benefit: Creates configured objects without repeating construction details.
 *
 * Tradeoff: Deep copies are difficult when an object has mutable nested objects.
 */
public final class PrototypePatternExample {
    private PrototypePatternExample() {
    }

    public static final class GameCharacter {
        private final String type;
        private int health;

        public GameCharacter(String type, int health) {
            this.type = type;
            this.health = health;
        }

        public GameCharacter copy() {
            return new GameCharacter(type, health);
        }

        public void takeDamage(int damage) {
            health -= damage;
        }

        @Override
        public String toString() {
            return type + " with health " + health;
        }
    }

    public static void main(String[] args) {
        GameCharacter original = new GameCharacter("Warrior", 100);
        GameCharacter copy = original.copy();
        copy.takeDamage(30);

        System.out.println("Original: " + original);
        System.out.println("Copy: " + copy);
    }
}