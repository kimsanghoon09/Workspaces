package p03.statics.constant_field;

public class EarthEx {

	public static void main(String[] args) {
		Earth earth = new Earth();
		
		System.out.println("지구 반지름 : " + earth.EARTH_RADIUS);
		System.out.println("지구 표면적 : " + earth.EARTH_SURFACE_AREA);

//		earth.EARTH_RADIUS = 2000;
		System.out.println("지구 반지름 : " + Earth.EARTH_RADIUS);
		System.out.println("지구 표면적 : " + Earth.EARTH_SURFACE_AREA);
		
		Earth.age = 50.2;
		System.out.println("지구 나이 : " + Earth.age + "억년");
		
	}

}
