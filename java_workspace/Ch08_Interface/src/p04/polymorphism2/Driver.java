package p04.polymorphism2;

public class Driver {
	
	// promotion
	public void drive(Vehicle vehicle) {
		vehicle.run();	// override한 것을 실행 => polymorphism
	}

}
