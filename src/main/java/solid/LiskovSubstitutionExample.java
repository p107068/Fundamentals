package solid;

/** Demonstrates that subtypes must honor the contract of their parent abstraction. */
public final class LiskovSubstitutionExample {
    private LiskovSubstitutionExample() {
    }

    public interface Bird {
        String name();
    }

    public interface FlyingBird extends Bird {
        void fly();
    }

    public static final class Sparrow implements FlyingBird {
        @Override
        public String name() {
            return "sparrow";
        }

        @Override
        public void fly() {
            System.out.println(name() + " is flying");
        }
    }

    public static final class Penguin implements Bird {
        @Override
        public String name() {
            return "penguin";
        }
    }

    public static void letBirdsLiveNaturally(Bird bird) {
        System.out.println(bird.name());
    }

    public static void letFlyingBirdsFly(FlyingBird bird) {
        bird.fly();
    }

    public static void main(String[] args) {
        letBirdsLiveNaturally(new Penguin());
        letFlyingBirdsFly(new Sparrow());
    }
}