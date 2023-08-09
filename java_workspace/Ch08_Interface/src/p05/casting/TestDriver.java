package p05.casting;

public class TestDriver {

	public static void main(String[] args) {
		Vehicle v = new Bus();	// promotion
		
		v.run();
		
//		v.checkFare();
		
		if (v instanceof Bus) {
			Bus bus = (Bus) v;
			bus.checkFare();
		}

	}

}
