package p04.final_class;

public final class Car {
	public int speed = 0;
	
	public void speedUp() {
		speed++;
	}

	public void speedDown() {
		speed--;
	}
	
	public void stop() {
		System.out.println();
		speed = 0;
	}
}
