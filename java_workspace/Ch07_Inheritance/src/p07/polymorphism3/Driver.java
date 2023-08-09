package p07.polymorphism3;

public class Driver {
	
	// promotion
	public void drive(Vehicle vehicle) {
		vehicle.run();	// override한 것을 실행 => polymorphism
	}

}
