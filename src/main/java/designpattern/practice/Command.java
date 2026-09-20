package designpattern.practice;

public class Command {
	
	private static interface Command_ {
		void execute();
	}
	
	private static class SwitchOnCommand implements Command_ {
		Light light;
		
		SwitchOnCommand(Light _light) {
			light = _light;
		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			light.switchOn();
		}
	}
	
	private static class SwitchOffCommand implements Command_ {
		Light light;
		
		SwitchOffCommand(Light _light) {
			light = _light;
		}

		@Override
		public void execute() {
			// TODO Auto-generated method stub
			light.switchOff();
		}
		
	}
	
	private static class Light {
		void switchOn() {
			System.out.println("Light switched on!");
		}
		void switchOff() {
			System.out.println("Light switched off!");
		}
	}
	
	private static class Remote {
		private Command_ command;
		
		Remote(Command_ _command) {
			command = _command;
		}
		
		void pressButton() {
			command.execute();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Light light = new Light();
		Command_  offCommand = new SwitchOffCommand(light);
		Command_  onCommand = new SwitchOnCommand(light);
		Remote remote = new Remote(offCommand);
		remote.pressButton();
		
		remote = new Remote(onCommand);
		remote.pressButton();
	}

}
