package ncs.test3;

public class TvTest {

	public static void main(String[] args) {
		Tv tvArray[] = new Tv[3];
		//Tv객체를 3개 생성하여 배열에 넣는다.
		tvArray[0] = new Tv("INFINIA", 1_500_000, "LED TV");
		tvArray[1] = new Tv("XCANVAS", 1_000_000, "LCD TV");
		tvArray[2] = new Tv("CINEMA", 2_000_000, "3D TV");
		//배열에 있는 객체정보를 모두 출력한다.
		for (int i = 0; i < tvArray.length; i++) {
			System.out.println(tvArray[i]);
		}
		
		//최대/최소 출력
		Tv maxTv=tvArray[0], minTv=tvArray[0];
		for (int i = 1; i < tvArray.length; i++) {
			//가격이 가장 비싼 제품
			if(maxTv.getPrice()<tvArray[i].getPrice())
				maxTv = tvArray[i];
			//가격이 가장 저렴한 제품			
			if(minTv.getPrice()>tvArray[i].getPrice())
				minTv = tvArray[i];
		}
		System.out.println("가격이 가장 비싼 제품 : "+maxTv.getName());
		System.out.println("가격이 가장 저렴한 제품 : "+minTv.getName());
	}

}
