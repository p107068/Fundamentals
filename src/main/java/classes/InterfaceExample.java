package classes;

public class InterfaceExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
interface Bird {
	void eat();
}
interface FlyingBird {
	void fly();
}
class Eagle implements Bird, FlyingBird {

	@Override
	public void fly() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eat() {
		// TODO Auto-generated method stub
		
	}
	
}
class Penguine implements Bird {

	@Override
	public void eat() {
		// TODO Auto-generated method stub
		
	}
	
}