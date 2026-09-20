package designpattern.tier2;

/**
 * Command Pattern
 *
 * Intent: Encapsulate an operation as an object.
 *
 * Use when: A request should be queued, logged, retried, scheduled, or undone later.
 *
 * Examples: Undo and redo; chess moves; remote-control buttons; background task execution.
 *
 * Structure: The caller (RemoteControl) invokes a Command, and the Command delegates
 * to a Receiver (Light) that performs the actual work.
 *
 * Benefit: Separates the request sender from the object that performs the operation.
 *
 * Tradeoff: Each operation usually needs its own command class.
 */
public final class CommandPatternExample {
    private CommandPatternExample() {
    }

    public interface Command {
        void execute();
    }

    public static final class Light {
        public void turnOn() {
            System.out.println("Light is on");
        }

        public void turnOff() {
            System.out.println("Light is off");
        }
    }

    public static final class TurnOnLightCommand implements Command {
        private final Light light;

        public TurnOnLightCommand(Light light) {
            this.light = light;
        }

        @Override
        public void execute() {
            light.turnOn();
        }
    }

    public static final class TurnOffLightCommand implements Command {
        private final Light light;

        public TurnOffLightCommand(Light light) {
            this.light = light;
        }

        @Override
        public void execute() {
            light.turnOff();
        }
    }

    public static final class RemoteControl {
        public void press(Command command) {
            command.execute();
        }
    }

    public static void main(String[] args) {
        Light light = new Light();
        RemoteControl remote = new RemoteControl();

        remote.press(new TurnOnLightCommand(light));
        remote.press(new TurnOffLightCommand(light));
    }
}